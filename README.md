# Proyecto XYZ

Este proyecto utiliza **Docker Compose** para levantar la aplicación junto con sus dependencias.

## 🚀 Levantar el entorno

Antes de iniciar, asegúrate de tener instalado **Docker** y **Docker Compose**.

1. Detener y limpiar los contenedores, redes y volúmenes anteriores:
   ```bash
   docker compose down -v
   docker compose up --build
