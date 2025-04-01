# Usa la imagen base de Node.js para compilar la aplicación
FROM node:22 AS build

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia los archivos del proyecto y la configuración
COPY package.json package-lock.json ./
RUN npm install --frozen-lockfile

# Copia el resto del código fuente
COPY . .

# Construye la aplicación
RUN npm run build

# Usa una imagen ligera de Nginx para servir la aplicación
FROM nginx:alpine

# Copia los archivos de la build al directorio de Nginx
COPY --from=build /app/dist /usr/share/nginx/html

# Expone el puerto 80
EXPOSE 80

# Comando por defecto para ejecutar Nginx
CMD ["nginx", "-g", "daemon off;"]
