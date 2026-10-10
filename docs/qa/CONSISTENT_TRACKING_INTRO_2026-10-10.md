# Seguimiento e Introducción — consistencia con el shell M3

## Implementado

El morado #512DA8 y las ilustraciones/Lottie originales se conservan.

### Seguimiento

- Scaffold y toolbar Material 3, con superficie tonal como el shell principal.
- Snackbar adaptador: mantiene el estado y los mensajes existentes.
- Panel inferior tonal con esquinas superiores redondeadas y separación de 12dp.
- Cronómetro monoespaciado con tipografía headlineMedium. Muestra HH:mm:ss;
  se ocultan centésimas solo en la presentación, sin cambiar el tiempo registrado.
- Acción principal con texto: iniciar, pausar o continuar. Guardar es secundaria
  delineada. Cancelar sigue separado en toolbar y mantiene su confirmación.
- Los callbacks existentes START/RESUME/SAVED no cambian; en esta API RESUME es
  el comando visual de pausa. Prueba de regresión conserva ese contrato.
- Insets consumidos una sola vez. La rama horizontal respeta el padding recibido
  y limita el panel de controles a 240dp en vez de cubrir todo el ancho.

### Introducción

- Fondo claro/oscuro, tipografía y colores de texto M3 iguales a la app.
- Botón siguiente/comenzar primario; anterior es secundario y se deshabilita
  correctamente en la primera página.
- Indicador morado/outlineVariant; se elimina la combinación cyan/blanco.
- Se conservan la zapatilla, Lottie de ubicación y Lottie de notificaciones;
  estas animaciones usan 180dp para equilibrar texto y acciones.
- Contenido desplazable, footer fuera del scroll y mensajes más concisos.
- Corregidos títulos Welcome/Bienvenido intercambiados en recursos ES/EN.
- No se modifican solicitudes de permisos ni finalización del onboarding.

## Verificación

Emulador Codex_UI_API_33, Android 13, 1080×2400.

- assembleDebug, assembleDebugAndroidTest y 26 pruebas JVM: aprobados.
- RunningConsistencyTest: **3/3 en inglés y 3/3 en español**, con repetición
  adicional 3/3 para validar capturas sincronizadas.
- Pruebas: acciones de tracking en WAITING/TRACKING/PAUSE; avanzar, volver e
  iniciar introducción; callbacks de ambos permisos; acceso a explicaciones
  mediante scroll; tema claro 100% y oscuro 200%.
- Capturas revisadas de introducción en fixtures de componentes de producción.
  No se reseteó el onboarding ni se borraron datos para forzar la pantalla real.
  Estas pruebas no abren el diálogo del sistema para permisos.
- App real: seguimiento en reposo, iniciar, ubicación simulada, polilínea visible
  y pausa. Captura oscura al 200% muestra cronómetro y ambas acciones legibles.
  La sesión de prueba se canceló sin guardar una carrera ficticia.
- No se repitió toda la suite histórica en esta entrega: se priorizó la suite
  enfocada y los tests JVM. No hubo commit ni publicación.

## Evidencia visual

`docs/qa/consistent-shell/tracking-es-light.png`: barra y acciones tonales claras
(captura inicial, anterior al redondeado final del panel).

`docs/qa/consistent-shell/tracking-active-dark-large.png`: app real con recorrido
activo al 200%, extremos redondeados y acciones Pausar/Guardar diferenciadas.
El estilo de mapa sigue siendo Lite porque es la preferencia elegida: el tema
oscuro no la sobrescribe automáticamente.

`docs/qa/consistent-shell/intro/intro-light-1.0-0.png`: bienvenida final en español.

`docs/qa/consistent-shell/intro/intro-dark-2.0-1.png` e
`docs/qa/consistent-shell/intro-en/intro-dark-2.0-1.png`: permiso de ubicación final
en español e inglés al 200%; textos e ilustración mantienen la jerarquía del shell.

La captura `tracking-paused-landscape.png` se tomó durante la rotación y muestra
contenido aún girado, con mapa sin cargar. **No valida paisaje**. Queda pendiente
una comprobación estable de esa orientación, tablet, API 36 y TalkBack.

La captura por UiAutomation requiere esperar al render de SurfaceFlinger además
del idle de Compose: la primera bienvenida se capturó vacía pese a tener semántica
visible. Se sincronizó con una espera de 300ms y se volvió a capturar/revisar.

## Key Learnings:

1. La consistencia no requiere pintar grandes áreas de morado: superficies tonales,
   textos emparejados y acción primaria morada mantienen mejor la jerarquía.
2. El nombre RESUME del enum de tracking no significa reanudar en la UI actual;
   cambiarlo durante una migración visual rompería el contrato de pausa.
3. El texto de onboarding largo necesita scroll independiente del footer y QA
   en ambos idiomas a fuente 200%.

## Handoff

Presentación de Seguimiento e Introducción implementada. Pendientes: detalle M3,
diálogos de cancelación/guardado, formularios y otras pantallas; no se dan por
completadas las fases 4/5 enteras. Archivos clave: TrackingScreen.kt,
ToolbarTracking.kt, TrackingControls.kt, MapAndTime.kt, TimeRun.kt,
IntroductionScreen.kt, ContainerItemIntro.kt, ItemIntroduction.kt,
ButtonsPrevAndNext.kt, strings.xml ES/EN y RunningConsistencyTest.kt.
