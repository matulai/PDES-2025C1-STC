import os
import subprocess
import sys
import platform
import json
import base64
from urllib import request, error
import time


ENV_FILE = ".env"
DOCKER_COMPOSE_FILE = "contenedores.yaml"
DOPPLER_API_URL = "https://api.doppler.com/v3/configs/config/secrets/download?format=json"

def get_doppler_token():
  
    token = os.getenv('DOPPLER_TOKEN')
    if token:
        print("Token de Doppler encontrado en la variable de entorno del sistema.")
        return token

    print("-" * 60)
    print("¡Hola! Para configurar el entorno, se necesita un Service Token de Doppler.")
    print("-" * 60)
    try:
        token = input("Pega el token aquí y presiona Enter: ").strip()
        if not token:
            print("No se ingresó ningún token. Abortando.", file=sys.stderr)
            sys.exit(1)
        
        print("Token obtenido para la sesión actual.")
        return token
    except KeyboardInterrupt:
        print("\nOperación cancelada por el usuario.", file=sys.stderr)
        sys.exit(1)

def fetch_secrets_from_api(token):
    print("Contactando la API de Doppler para obtener los secretos...")
    
    auth_string = f"{token}:"
    encoded_auth = base64.b64encode(auth_string.encode("utf-8")).decode("utf-8")
    
    headers = {
        "Authorization": f"Basic {encoded_auth}",
        "accept": "application/json"
    }

    req = request.Request(DOPPLER_API_URL, headers=headers)
    
    try:
        with request.urlopen(req) as response:
            if response.status != 200:
                print(f"Error: Fallo al obtener secretos de Doppler. Código de estado: {response.status}", file=sys.stderr)
                print(f"Respuesta: {response.read().decode('utf-8')}", file=sys.stderr)
                sys.exit(1)
            
            response_body = response.read().decode("utf-8")
            secrets = json.loads(response_body)
            print("Secretos descargados correctamente.")
            return secrets
            
    except error.URLError as e:
        print(f"Error de red al contactar la API de Doppler: {e.reason}", file=sys.stderr)
        if hasattr(e, 'code'):
             print(f"Código de error HTTP: {e.code}", file=sys.stderr)
        sys.exit(1)
    except json.JSONDecodeError:
        print("Error: No se pudo decodificar la respuesta JSON de Doppler.", file=sys.stderr)
        sys.exit(1)

def create_env_file(secrets):
    print(f"Creando el archivo de entorno '{ENV_FILE}'...")
    try:
        with open(ENV_FILE, 'w', encoding='utf-8') as f:
            for key, value in secrets.items():
                f.write(f"{key}={value}\n")
        print(f"Archivo '{ENV_FILE}' creado con éxito.")
    except IOError as e:
        print(f"Error al escribir en el archivo '{ENV_FILE}': {e}", file=sys.stderr)
        sys.exit(1)

def run_docker_compose():
    if not os.path.exists(DOCKER_COMPOSE_FILE):
        print("Error: No se encuentra el archivo 'contenedores.yaml'. Asegúrate de que exista en la raíz del proyecto.", file=sys.stderr)
        sys.exit(1)

    print("\nLevantando los contenedores desde 'contenedores.yaml'...")

    commands = [
        ["docker", "compose", "-f", DOCKER_COMPOSE_FILE, "up", "-d"],
        ["docker-compose", "-f", DOCKER_COMPOSE_FILE, "up", "-d"]
    ]

    for command in commands:
        try:
            subprocess.run(command, check=True)
            print("\n¡Contenedores iniciados con éxito!")
            print("Puedes ver los logs con: docker compose -f contenedores.yaml logs -f")
            return
        except (subprocess.CalledProcessError, FileNotFoundError):
            print(f"El comando {' '.join(command)} falló. Probando el siguiente...")

    print("\nError: No se pudo ejecutar ningún comando de Docker Compose.", file=sys.stderr)
    print("Asegúrate de que Docker esté instalado y corriendo.", file=sys.stderr)
    sys.exit(1)

def run_docker_compose_down():
    print("\nDeteniendo contenedores...")

    commands = [
        ["docker", "compose", "-f", DOCKER_COMPOSE_FILE, "down"],
        ["docker-compose", "-f", DOCKER_COMPOSE_FILE, "down"]
    ]

    for command in commands:
        try:
            subprocess.run(command, check=True)
            print("Contenedores detenidos correctamente.")
            return
        except (subprocess.CalledProcessError, FileNotFoundError):
            print(f"Error al ejecutar {' '.join(command)}. Probando el siguiente...", file=sys.stderr)

    print("No se pudo detener los contenedores. Puede que necesites hacerlo manualmente.", file=sys.stderr)

def cleanup():
    """Ejecuta la limpieza: detiene contenedores y borra .env."""
    run_docker_compose_down()
    if os.path.exists(ENV_FILE):
        print(f"Eliminando el archivo '{ENV_FILE}'...")
        os.remove(ENV_FILE)
        print("Archivo .env eliminado.")

def main():
    """Flujo principal del script."""
    try:
        doppler_token = get_doppler_token()
        secrets = fetch_secrets_from_api(doppler_token)
        create_env_file(secrets)
        run_docker_compose()
        print("\n" + "="*60)
        print("¡Entorno iniciado! Los contenedores están corriendo.")
        print("\nESTE SCRIPT SEGUIRÁ CORRIENDO PARA GESTIONAR EL ENTORNO.")
        print("Presiona Ctrl+C en esta terminal para detener los contenedores y limpiar.")
        print("="*60)
        while True:
            time.sleep(5)
    except KeyboardInterrupt:
        print("\n\nInterrupción por teclado detectada. Procediendo a la limpieza...")
    finally:
        cleanup()
        print("\nLimpieza completada.")

if __name__ == "__main__":
    main()
