# Usa la imagen base de Node.js para compilar la aplicación
FROM node:22-slim AS build

# Declara el argumento de construcción que se pasará desde docker-compose.yaml
ARG VITE_API_URL

# Establece la variable de entorno VITE_API_URL para que esté disponible durante el proceso de build
# Es importante que las variables que Vite debe procesar comiencen con VITE_
ENV VITE_API_URL=${VITE_API_URL}

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