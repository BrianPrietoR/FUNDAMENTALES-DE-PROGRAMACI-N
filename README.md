/*
INSTRUCCIONES DE USO (Eclipse - Java 8)


1) Crear un nuevo proyecto Java en Eclipse (File > New > Java Project).
- Project name: ProyectoVentas
- Java compliance: 1.8


2) Copiar el contenido de este canvas en los archivos .java dentro de la carpeta 'src' del proyecto.
Archivos esperados (nombres EXACTOS):
- GenerateInfoFiles.java (contiene la clase GenerateInfoFiles)
- main.java (contiene la clase main - segundo main exigido)
- Product.java
- Salesman.java


NOTA: Mantén el package 'com.poli.ventas' declarado en cada archivo.


3) Crear carpetas en la raíz del proyecto (fuera de src):
- input/
- products.txt (será generado automáticamente por GenerateInfoFiles)
- salesmen_info.txt
- sales/ (aquí se crearán los archivos de ventas por vendedor)
- output/ (aquí Main escribirá los reportes)


Si prefieres, ejecuta GenerateInfoFiles para que genere automáticamente la carpeta 'input' y los archivos de prueba.


4) Ejecutar GenerateInfoFiles:
- Run As > Java Application > seleccionar GenerateInfoFiles
- Al terminar verás el mensaje de éxito y los archivos aparecerán en 'input/'.


5) Ejecutar main (la clase 'main'):
- Run As > Java Application > seleccionar main
- Al terminar verás los archivos de salida en 'output/report_vendedores.csv' y 'output/report_productos.csv'


6) Recomendaciones de evaluación y pruebas:
- Verifica manualmente algunas ventas para asegurar que las multiplicaciones (precio*cantidad) sean correctas.
- Revisa la consola para mensajes de archivos con formato inválido o productos desconocidos.
- Para cambiar la cantidad de productos o vendedores generados, modifica las constantes en GenerateInfoFiles.main o pasa parámetros (se pueden agregar argumentos si lo deseas).


*/
