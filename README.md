## Laboratorio 3: Gestión de aulas

Aplicación de escritorio hecha con JavaFX, Hibernate y MySQL para registrar las aulas de la universidad. Tiene un login y un formulario para guardar aulas.

## Qué hay en el repositorio

- `aulas-archetype/`: el arquetipo de Maven que creé como plantilla del proyecto.
- `aulas/`: el proyecto que se genera a partir de ese arquetipo.

## Cómo probarlo

1. Enciende MySQL (yo uso el de XAMPP).
2. Abre la carpeta `aulas` en NetBeans y dale Run.
3. Entra con el usuario de prueba:
   - Correo: `admin@ufg.edu`
   - Clave: `1234`

La base de datos y las tablas se crean solas la primera vez que se ejecuta. Si tu MySQL tiene otro usuario o clave, cámbialos en `src/main/resources/hibernate.cfg.xml`.

## Cómo instalar el arquetipo

1. Abre `aulas-archetype` en NetBeans.
2. Clic derecho en el proyecto, Run Maven, Goals, y escribe `clean install`.
3. Cuando salga `BUILD SUCCESS`, ya se puede usar en File, New Project, Project from Archetype (Group Id `edu.ufg`, Artifact Id `aulas-archetype`, Version `1.0.0`).

## Validaciones del formulario

- No se puede dejar ningún campo vacío.
- Planta y capacidad deben ser números mayores que 0.
- No se puede repetir el mismo nombre de aula en el mismo edificio.

## Cómo lo trabajé

Hice cada cambio en una rama propia (`feature/...`) y los uní a `main` con Pull Requests.