# RunningCompose — Animaciones, rutas, idiomas y color

## Cambios

- Restaurada la animación Lottie original `empty1` en Carreras (160dp, antes
  250dp). Se mantienen tipografía M3, texto de ayuda y desplazamiento para letra
  grande. No se eliminaron recursos Lottie ni se sustituyeron las otras animaciones.
- Primario de marca **#512DA8 conservado**. Fondo claro pasa de #FFF7FF a #FCFAFF:
  menos rosado, con superficies lavanda y acento teal existentes. Tema oscuro y
  colores de ruta guardados no se cambian.
- Polilíneas con extremos/uniones redondeados en tracking, detalle y vista previa.
- Vista previa de Ajustes usa un circuito a escala de una carrera en vez de unir
  ciudades de distintos países. Es una muestra, no una ubicación del usuario.
- Eliminada la coordenada de depuración superpuesta al mapa.
- Navegación abreviada: **Carreras / Progreso / Ajustes** y **Runs / Progress /
  Settings**. Las rutas no cambian. Las etiquetas largas españolas se truncaban al
  200%; se añadió una aserción de elipsis y altura para evitar esa regresión.

## Problema encontrado al mover la ubicación

Antes: el cronómetro funcionaba y la cámara seguía la ubicación, pero la ruta no
se dibujaba. `TrackingServices.onStartCommand` omitía llamar a su superclass
`LifecycleService`; por ello su colector, limitado al estado STARTED, no recibía
ubicaciones. Se añadió `super.onStartCommand(...)`.

Verificación manual en app real del emulador API 33: posiciones consecutivas
inyectadas con `adb emu geo fix`, separadas por 3 segundos y con distancias a escala
de carrera. Después del cambio aparece la polilínea. Pausar, mover la ubicación
y reanudar produce dos segmentos separados, sin una recta sobre el salto.
También se cambió de español a inglés conservando la sesión en curso.

Además, al reconstruir el dibujo por cambio de configuración ahora se eliminan
los objetos Polyline del mapa, no solo las referencias Kotlin. La lista de
segmentos vacía también se admite sin `last()` sobre una lista vacía.

Las sesiones simuladas se cancelaron; no se guardaron carreras ficticias en Room.
No se cambiaron filtros de GPS, límites de velocidad, almacenamiento ni permisos.

## Evidencia

- `visual-refinement/route-before.png`: cronómetro sin ruta, antes de corregir el servicio.
- `visual-refinement/route-es.png`: ruta visible en español después de corregirlo.
- `visual-refinement/route-en-paused-segments.png`: dos tramos al pausar/reanudar, en inglés.
- `visual-refinement/empty-es.png`, `empty-en.png`: Lottie original restaurado.
- `visual-refinement/empty-*-large.png`: estado vacío al 200%, antes de abreviar navegación.
- `visual-refinement/spanish-nav-large-final.png`: app real al 200% con etiquetas abreviadas.
- `visual-refinement/maps/`: comparación controlada de cuatro colores sobre mapas reales.

Las capturas de rutas reales preceden al redondeado final de extremos. Las cuatro
comparativas controladas sí incluyen ese acabado. Las comparativas de colores
usan dos segmentos ficticios, con ancho 10px; no cambian preferencias del usuario.

## Elección de colores

| Mapa | Propuesta | Motivo observado |
|---|---|---|
| Lite/claro | Morado #512DA8 | Se distingue de calles grises y refuerza la marca |
| Lite/claro, alternativa | Teal #006A60 | Contraste visible sin competir con el morado de la UI |
| Midnight/oscuro | Lavanda #CFBCFF | Conserva la familia morada y destaca sobre el azul oscuro |
| Midnight/oscuro, alternativa | Ámbar #FFC857 | Muy visible, pero reservarlo para ruta, no para botones primarios |

No se aplican cambios automáticos al color de una ruta: se respeta la elección
guardada. El negro actual funciona en Lite, pero no es una elección conveniente
para Midnight. Estas son conclusiones de las capturas de esos estilos, no una
garantía de contraste sobre todos los mapas ni condiciones de luz.

## Pruebas

Emulador Codex_UI_API_33 (Android 13, 1080×2400). Compilación debug y androidTest,
26 pruebas JVM. Suite completa en inglés: 21 instrumentadas; Carreras en español:
10 instrumentadas. Las pruebas incluyen claro/oscuro y escala de fuente 100%/200%.

Nueva `RunningMapVisualTest`: mapa SDK real, lista vacía repetida, actualización de
puntos sin recrear línea, reconstrucción por color/estilo, dos segmentos,
propiedades de color/ancho, limpieza final y cuatro capturas. No simula el
servicio GPS: ese recorrido se verificó manualmente como se describe arriba.

Una repetición posterior de la suite completa tuvo un fallo al destruir la
Activity del catálogo (quedó PAUSED); el siguiente lanzamiento instrumentado
reportó `System has crashed`. La suite inicial de 21 había pasado. Se reinició
el emulador y se repitieron por separado las comprobaciones finales de idiomas
y catálogo: **10/10 en español y 14/14 en inglés aprobadas**, incluyendo las
aserciones nuevas contra etiquetas truncadas. No se atribuye la caída a una
causa de aplicación sin evidencia.

La aserción de navegación comprueba elipsis y desbordamiento vertical. Se descartó
`hasVisualOverflow` como comprobación indiscriminada: Compose reporta un párrafo de
346px dentro de un texto medido de 77px para “Runs”, aunque no hay elipsis ni
recorte de altura. No se ignoran las elipsis reales de etiquetas largas.

## Mejoras siguientes de diseño

1. Migrar la zona de cronómetro/acciones de tracking a superficies M3, conservando
   morado para la acción principal y usando menos bloques grandes saturados.
2. Ofrecer presets de color por estilo de mapa, con nombre y muestra; mantener
   siempre el selector libre y no sobrescribir decisiones guardadas.
3. Desplazar encabezado y filtros con la lista en pantallas bajas/letra grande.
4. Revisar reducción de movimiento para las animaciones y probar TalkBack,
   paisaje, tablet y API 36. No se afirma cobertura de esos escenarios.

## Key Learnings:

1. LifecycleService requiere la llamada superclass en onStartCommand para activar
   colectores dependientes de STARTED; un cronómetro activo no prueba registro GPS.
2. Limpiar una lista de referencias Polyline no elimina las líneas del mapa:
   hay que invocar remove antes de reconstruirlas.
3. Las etiquetas españolas necesitan QA propio a fuente 200%; las etiquetas
   inglesas que caben no garantizan que la traducción también quepa.
4. La animación original puede convivir con M3 ajustando tamaño y composición,
   sin reemplazar la identidad visual de la aplicación.

## Handoff

Cambios locales, sin commit ni publicación. Primario y colores guardados intactos.
Siguiente trabajo: tracking M3/presets solo si se continúa esa fase. Archivos clave:
EmptyRuns.kt, ColorScheme.kt, HomeNavigationBar.kt, navigation_material3.xml,
TrackingServices.kt, MapTracking.kt, MapFromConfig.kt, MapRunItem.kt,
RunningPhase3Test.kt y RunningMapVisualTest.kt.
