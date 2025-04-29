# Usa la imagen base de Node.js para compilar la aplicación
FROM node:22-slim AS build

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia los archivos del proyecto y la configuración
COPY package.json package-lock.json ./
RUN npm ci

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

# # Usa la imagen base de Node.js para compilar la aplicación
# FROM node:22@sha256:3c5e5b6b6b6e5b6b6b6e5b6b6b6e5b6b6b6e5b6b6b6e5b6b6b6e5b6b6b6 AS build

# # Usa una imagen ligera de Nginx para servir la aplicación
# FROM nginx@sha256:ad4b9c3e5b6b6b6e5b6b6b6e5b6b6b6e5b6b6b6e5b6b6b6e5b6b6b6e5b6b6b6