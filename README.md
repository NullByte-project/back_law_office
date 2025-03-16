# Proyecto FastAPI

Este es un proyecto básico utilizando FastAPI.

## Requisitos

- Python 3.12
- FastAPI
- Uvicorn

## Instalación

1. Clona el repositorio:

    ```sh
    git clone <URL_DEL_REPOSITORIO>
    cd <NOMBRE_DEL_REPOSITORIO>
    ```

2. Crea un entorno virtual y actívalo:

    ```sh
    python -m venv venv
    source venv/bin/activate  # En Windows usa `venv\Scripts\activate`
    ```

3. Instala las dependencias:

    ```sh
    pip install -r requirements.txt
    ```

## Ejecución

Para ejecutar la aplicación, usa el siguiente comando:

```sh
uvicorn main:app --reload