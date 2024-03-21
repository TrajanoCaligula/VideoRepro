# VideoRepro

Configuración para la ejecución
Para ejecutar el proyecto con normalidad solo se requiere comprobar dos elementos: 
En el directorio “VideoRepro\src\main\webapp\”  debe existir la carpeta “uploads”. Ahí se almacenarán los videos que suban los usuarios.
Se debe cambiar el path de “uploads” según donde se ejecute, ya que se utiliza el path absoluto. En el archivo web.xml situado en “VideoRepro\src\main\webapp\WEB-INF\”, debe contener el path absoluto de la carpeta “uploads”, mencionada anteriormente.
Concretamente debe estar en la línea 64 de ese documento.

TODO:
- Rest (seguir Anexo, podemos tenr uno o más, como queramos)
- Buscador
    • búsqueda por título
    • búsqueda por autor
    • búsqueda por fecha de creación (cualquier combinación de fecha es posible, sólo año,
      sólo mes/año o día/mes/año)
- Reproductor podemos seguir utilizando la misma vista pero que esta pida a través del servlet nuevo.
- Tema clase de base de datos, la profe ha dicho que mejor creemos una nueva
