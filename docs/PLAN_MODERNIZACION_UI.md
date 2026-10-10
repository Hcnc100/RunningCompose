# RunningCompose — Plan de modernización visual

Fecha: 2026-10-10. Estado: fases 1, 2 y 3 implementadas.

## Avance de implementación

- Fase 1: inventario (63 archivos con imports M2), baseline compilado y capturas
  en emulador Android 13/API 33. Cambios locales preexistentes preservados.
- Fase 2: Material 3 1.3.2 administrado por el BOM existente; paletas completas
  claro/oscuro, tipografía, formas y espaciado; catálogo visual y pruebas.
  Complementada con componentes reutilizables M3 y pruebas de interacción:
  `docs/qa/MATERIAL3_PHASE2_COMPONENTS_2026-10-10.md` (26 JVM y 10 instrumentadas).
- Puente temporal: M2 comparte superficies, texto y formas, pero conserva los
  tonos primario/secundario anteriores porque hay barras con texto blanco/negro
  fijo. Migrar esos componentes antes de retirar esta excepción.
- Verificación: 26 tests JVM y 5 instrumentados aprobados; capturas y análisis
  en `docs/qa/MATERIAL3_FOUNDATION_2026-10-10.md`.
- Fase 3: shell M3 (toolbar y navegación), Carreras, tarjetas adaptables,
  ordenación, selección y estados vacío/carga/error con reintento. Se conservan
  navegación, callbacks y estados de snackbar existentes. QA: 26 JVM y 20
  instrumentadas aprobadas; `docs/qa/MATERIAL3_PHASE3_2026-10-10.md`.
- Seguimiento e Introducción: presentación unificada con el shell M3; ver
  `docs/qa/CONSISTENT_TRACKING_INTRO_2026-10-10.md`. Avance parcial de fases 4/5:
  detalle, diálogos, formularios y contenido de Ajustes/Estadísticas pendientes.
  No se publicó.

## 1. Referencia y diagnóstico

Análisis del código y del sistema de diseño documentado de
`D:/Documents/Projects/Android/NurseCompose2`. No se ejecutaron las apps ni se
validó visualmente su versión instalada; las conclusiones describen las fuentes locales.

| Aspecto | NurseCompose2 | RunningCompose actual |
|---|---|---|
| Componentes | Material 3, dependencia explícita 1.3.2 | Material 2 |
| Kotlin/plugin Compose | 2.0.0 | 2.0.0 |
| Tema | Esquemas completos claro/oscuro | Paletas parciales, mismo acento morado en ambos modos |
| Superficies | Fondo, surfaceContainer y contenedores seleccionados | Valores predeterminados y colores de componente |
| Formas | Componentes 16 dp, diálogos/sheets 24 dp | small 10 dp, medium 4 dp, large 0 dp |
| Navegación | NavigationBar tonal, indicador de selección | BottomNavigation morada, texto inactivo blanco |
| Tipografía | Material 3; bodyLarge 16/24 sp | Material 2; body1 16 sp |
| Validación de referencia | Convención de contraste y QA documentada | Incorporar equivalente al migrar |

La elegancia de Nurse proviene de la coherencia: superficies suaves, contraste
semántico, selección clara y geometría uniforme. No de una fuente especial ni
de actualizar únicamente dependencias. Su documentación separa los colores de
gráficos de los de acciones y exige una sola toolbar con insets bien definidos.

**Aclaración:** Kotlin 2.0 y Compose Compiler 2.0 no equivalen a «Compose 2» como
sistema visual. Running ya tiene esa base. El cambio pendiente es Material 2 → 3.
No copiar ciegamente el Gradle de Nurse: conserva configuración antigua
`kotlinCompilerExtensionVersion = "1.4.3"` junto al plugin nuevo.

## 2. Dirección propuesta: deportivo, sobrio y legible

- Conservar el morado de Running como identidad; no copiar el rosa sanitario.
- Fondos neutros con matiz violeta, superficies tonales y elevación discreta.
- Morado para iniciar/reanudar y selección; error para cancelar/eliminar.
- Definir pares claros/oscuros para cada rol, evitando reutilizar el mismo
  morado oscuro como acción en modo oscuro sin medir contraste.
- Radios de 16 dp para tarjetas/campos; 24 dp para diálogos y paneles.
- Espaciado propuesto: escala 4/8/12/16/24/32 dp y margen habitual de 16 dp.
- Jerarquía Material 3: titleLarge, titleMedium, bodyLarge, bodyMedium y labelLarge.
  Cronómetro y distancia destacados; textos secundarios discretos pero legibles.
- No introducir glassmorphism, degradados decorativos ni animaciones continuas.
- Mantener inicialmente las tres pestañas actuales y sus estados restaurados.

Estos valores son una propuesta a comprobar con previews y capturas, no un tema
ya implementado ni una paleta cuyo contraste esté certificado.

## 3. Propuesta por pantalla

### Carreras — primera pantalla piloto
Mantener historial, filtros, ordenación y selección múltiple. Tarjetas tonales con
miniatura del recorrido, fecha, distancia principal y métricas secundarias.
Filtros en controles Material 3 compactos; acción «Iniciar carrera» inequívoca.
Cuando exista sesión activa, mostrar «Continuar carrera» según el estado real.
Diseñar vacío, carga, error y permisos sin ocultar las acciones útiles.

### Tracking — prioridad funcional
Mapa protagonista y panel de métricas legible, con cronómetro principal.
Controles grandes para iniciar, pausar, reanudar y guardar, usando los estados
actuales y sin reinterpretar su lógica por el nombre de una acción.
Separar cancelar de la acción principal y mantener confirmación de descarte.
Revisar el texto directo de ubicación que hoy dibuja TrackingScreen: no dejar
datos crudos de depuración en el diseño final sin una función explícita.
Respetar color de ruta elegido por el usuario y ciclo de vida del mapa.

### Detalle y estadísticas
Detalle: recorrido, fecha y resumen de métricas con la misma tarjeta semántica.
Estadísticas: indicadores resumidos y gráficos con leyendas y unidades claras.
No confundir colores de series con estados destructivos/seleccionados.
Conservar cálculos, unidades, datos y filtros existentes; no inventar métricas.

### Ajustes, perfil e introducción
Agrupar preferencias en secciones con títulos y texto de apoyo. Migrar campos,
selectores, diálogos y botones manteniendo unidades, perfil y personalización
del mapa. Ajustar formularios al teclado, fuente grande y landscape.

## 4. Implementación por fases

1. **Baseline y compatibilidad.** Revisar diff local antes de tocar HomeScreen;
   registrar capturas actuales claro/oscuro y pruebas existentes. Inventariar
   imports Material 2, snackbar/scaffold states y bibliotecas visuales externas.
   Elegir versiones compatibles y estables en ese momento, no actualizar todo.
2. **Fundación.** Añadir Material 3 y definir esquemas completos, formas,
   tipografía y espaciado. Crear previews de botones, tarjetas, campos, selección
   y estados. Migrar por flujos coherentes; si conviven M2/M3 temporalmente,
   mantener ambos temas alineados y evitar componentes M2 sin tematizar.
3. **Shell y piloto.** Migrar toolbar, NavigationBar y Carreras juntos. Conservar
   rutas, back stack, selección y scroll. Aprobar capturas antes de expandir.
4. **Tracking y detalle.** Migrar presentación sin cambiar servicio GPS,
   persistencia, recuperación de sesión ni guardado del recorrido.
5. **Resto de pantallas.** Estadísticas, ajustes, perfil, introducción, permisos,
   diálogos y estados compartidos. Auditar componentes de terceros caso a caso.
6. **Cierre.** Eliminar uso residual de componentes Material 2 cuando proceda;
   distinguir artefactos de iconos de componentes M2. Pruebas, capturas y
   validación en dispositivo antes de una publicación separada y autorizada.

No hacer una sustitución global de imports: cambian APIs de Scaffold,
SnackbarHostState, colores, tipografía y componentes de navegación.
Definir por pantalla quién consume insets; preservar las correcciones existentes
de Scaffolds anidados y padding de listas. Evitar barras duplicadas y padding doble.

## 5. Superficie de cambio prevista

Base: `app/build.gradle.kts`, `ui/theme/{Theme,Color,Type,Shape}.kt`.
Shell: `ui/screens/home/HomeScreen.kt`, `ui/screens/main/MainScreen.kt`,
`ui/share/SelectToolbar.kt` y estados asociados a Scaffold/snackbar.
Piloto: `ui/screens/runs/` y componentes compartidos.
Después: `ui/screens/tracking/`, detalle de carrera, `statistics/`, `config/`,
`editInfo/` e `intro/`. Verificar símbolos concretos al implementar.

No cambiar Room, modelos, servicio de tracking, repositorios, navegación,
identificador de aplicación, firma ni CI/CD como parte del rediseño.
No agregar login ni Firebase.

## 6. Criterios de aceptación

- Compilación y tests relevantes en cada fase; tests instrumentados para flujos UI.
- Contraste de texto normal ≥ 4.5:1 y objetivos táctiles ≥ 48 dp.
- Claro/oscuro, TalkBack, fuente 200%, landscape y teclado abierto.
- Sin solapamientos con barras, mapa, FAB, diálogos o acciones inferiores.
- Historial: filtrar, ordenar, seleccionar, eliminar y abrir detalle sin regresión.
- Tracking: iniciar, pausar, reanudar, guardar/cancelar, background y recuperación
  tras recreación/proceso según comportamiento soportado por el producto.
- Persistencia, unidades, cálculos y color de ruta sin cambios.
- No despliegue automático como consecuencia de este documento.

## 7. Próximo paso recomendado

Implementar primero fundamento visual + shell + Carreras en una entrega acotada.
Evaluar capturas reales antes de migrar tracking. El éxito se mide por coherencia,
legibilidad y ausencia de regresiones, no por usar la dependencia más reciente.

## Referencias

- Nurse: `D:/Documents/Projects/Android/NurseCompose2/docs/UI_DESIGN_SYSTEM.md`,
  tema y HomeBottomBar locales.
- [Migración de Material 2 a Material 3](https://developer.android.com/develop/ui/compose/designsystems/material2-material3).
- [Plugin Compose Compiler y Kotlin 2.0](https://kotlinlang.org/docs/compose-compiler-migration-guide.html).
