document.addEventListener("DOMContentLoaded", () => {

    const boton = document.getElementById("btnIniciarScanner");
    const video = document.getElementById("cameraPreview");
    const placeholder = document.getElementById("scannerPlaceholder");

    const estado = document.getElementById("scannerEstado");
    const detalle = document.getElementById("scannerDetalle");
    const punto = document.getElementById("scannerEstadoPunto");
    const piezaResultado =
        document.getElementById("piezaResultado");

    const piezaNombre =
        document.getElementById("piezaNombre");

    const piezaCodigo =
        document.getElementById("piezaCodigo");

    const piezaEstado =
        document.getElementById("piezaEstado");

    const piezaUbicacion =
        document.getElementById("piezaUbicacion");

    const piezaFabricante =
        document.getElementById("piezaFabricante");

    const piezaReferencia =
        document.getElementById("piezaReferencia");

    const piezaDescripcion =
        document.getElementById("piezaDescripcion");

    const maquinaNombre =
        document.getElementById("maquinaNombre");

    const maquinaCodigo =
        document.getElementById("maquinaCodigo");

    const maquinaArea =
        document.getElementById("maquinaArea");

    const maquinaUbicacion =
        document.getElementById("maquinaUbicacion");

    const confirmacion = document.getElementById("scannerConfirmacion");
    const marcoQr = document.getElementById("scannerMarcoQr");
    const btnIniciarInpeccion = document.getElementById("btnIniciarInspeccion");















    let stream = null;
    let escaneando = false;
    let detector = null;
    let detectandoFrame = false;
    let idPiezaSeleccionada = null;


    /*
     * =========================================================
     * VERIFICAR SOPORTE QR
     * =========================================================
     */

    if (!("BarcodeDetector" in window)) {

        estado.textContent = "Lector QR no disponible";

        detalle.textContent =
            "Este navegador no soporta el lector QR nativo.";

        boton.disabled = true;

        return;
    }


    detector = new BarcodeDetector({
        formats: ["qr_code"]
    });


    /*
     * =========================================================
     * INICIAR CÁMARA
     * =========================================================
     */

    async function iniciarCamara() {

        try {

            stream =
                await navigator.mediaDevices.getUserMedia({

                    video: {

                        facingMode: {
                            ideal: "environment"
                        }

                    },

                    audio: false

                });


            video.srcObject = stream;

            await video.play();


            video.classList.remove("hidden");

            placeholder.classList.add("hidden");


            escaneando = true;


            estado.textContent =
                "Escaneando...";

            detalle.textContent =
                "Apunta la cámara al código QR de la pieza.";


            punto.classList.remove("bg-gray-400");
            punto.classList.add("bg-violet-500");


            boton.textContent =
                "Detener cámara";


            requestAnimationFrame(
                analizarCamara
            );

        } catch (error) {

            console.error(
                "Error al acceder a la cámara:",
                error
            );


            estado.textContent =
                "No se pudo abrir la cámara";

            detalle.textContent =
                "Verifica los permisos de cámara del navegador.";
        }

        piezaResultado.classList.add(
            "hidden"
        );
    }


    /*
     * =========================================================
     * DETENER CÁMARA
     * =========================================================
     */

    function detenerCamara() {

        escaneando = false;


        if (stream) {

            stream
                .getTracks()
                .forEach(
                    track => track.stop()
                );

            stream = null;
        }


        video.srcObject = null;

        video.classList.add("hidden");

        placeholder.classList.remove("hidden");


        boton.textContent =
            "Activar cámara";
    }


    /*
     * =========================================================
     * ANALIZAR VIDEO
     * =========================================================
     */

    async function analizarCamara() {

        if (!escaneando) {
            return;
        }


        if (
            video.readyState >= 2 &&
            !detectandoFrame
        ) {

            detectandoFrame = true;


            try {

                const codigos =
                    await detector.detect(video);


                if (codigos.length > 0) {

                    const contenido =
                        codigos[0].rawValue;


                    qrDetectado(contenido);

                    return;
                }

            } catch (error) {

                console.error(
                    "Error detectando QR:",
                    error
                );

            } finally {

                detectandoFrame = false;
            }
        }


        requestAnimationFrame(
            analizarCamara
        );
    }

    function mostrarValor(valor) {

        if (
            valor === null ||
            valor === undefined ||
            String(valor).trim() === ""
        ) {
            return "Sin registro";
        }

        return valor;
    }


    /*
     * =========================================================
     * ESPERAR PROCESAMIENTO
     * =========================================================
     */

    function esperar(milisegundos) {
        return new Promise(
            resolve => setTimeout(
                resolve,
                milisegundos)
        );
    }


    /*
     * =========================================================
     * QR DETECTADO
     * =========================================================
     */

    async function qrDetectado(contenido) {

        detenerCamara();

        placeholder.classList.add("hidden");
        marcoQr.classList.add("hidden");


        /*
         * =====================================================
         * CONFIRMACIÓN INMEDIATA
         * =====================================================
         */

        confirmacion.classList.remove(
            "hidden"
        );
        placeholder.classList.add("hidden");
        marcoQr.classList.add("hidden");

        piezaResultado.classList.add(
            "hidden",
            "opacity-0",
            "translate-y-4"
        );


        estado.textContent =
            "QR leído";

        detalle.textContent =
            "Procesando información de la pieza...";


        punto.classList.remove(
            "bg-gray-400",
            "bg-violet-500",
            "bg-red-500"
        );

        punto.classList.add(
            "bg-green-500"
        );


        /*
         * Vibración corta en celulares compatibles
         */

        if ("vibrate" in navigator) {

            navigator.vibrate(
                [120, 60, 120]
            );
        }


        boton.disabled = true;

        boton.textContent =
            "Procesando...";


        /*
         * Pausa para que el usuario perciba
         * claramente que el QR fue leído.
         */

        await esperar(1200);


        estado.textContent =
            "Consultando pieza...";

        detalle.textContent =
            "Buscando información en ScannerCP.";


        try {

            const respuesta =
                await fetch(
                    `/api/piezas/qr/${encodeURIComponent(contenido)}`
                );


            if (!respuesta.ok) {

                throw new Error(
                    "No se encontró la pieza"
                );
            }


            const pieza =
                await respuesta.json();

            idPiezaSeleccionada = pieza.idPieza;


            /*
             * =====================================================
             * PIEZA
             * =====================================================
             */

            piezaNombre.textContent =
                mostrarValor(pieza.nombre);

            piezaCodigo.textContent =
                mostrarValor(pieza.codigo);

            piezaEstado.textContent =
                mostrarValor(pieza.estado);

            piezaUbicacion.textContent =
                mostrarValor(pieza.ubicacion);

            piezaFabricante.textContent =
                mostrarValor(pieza.fabricante);

            piezaReferencia.textContent =
                mostrarValor(pieza.referencia);

            piezaDescripcion.textContent =
                mostrarValor(pieza.descripcion);


            /*
             * =====================================================
             * MÁQUINA
             * =====================================================
             */

            maquinaNombre.textContent =
                mostrarValor(pieza.nombreMaquina);

            maquinaCodigo.textContent =
                mostrarValor(pieza.codigoMaquina);

            maquinaArea.textContent =
                mostrarValor(pieza.areaMaquina);

            maquinaUbicacion.textContent =
                mostrarValor(pieza.ubicacionMaquina);


            /*
             * =====================================================
             * OCULTAR CONFIRMACIÓN
             * =====================================================
             */

            confirmacion.classList.add(
                "hidden"
            );
            placeholder.classList.remove("hidden");
            marcoQr.classList.remove("hidden")


            /*
             * =====================================================
             * MOSTRAR TARJETA CON ANIMACIÓN
             * =====================================================
             */

            piezaResultado.classList.remove(
                "hidden"
            );


            requestAnimationFrame(() => {

                piezaResultado.classList.remove(
                    "opacity-0",
                    "translate-y-4"
                );

            });


            estado.textContent =
                "Pieza identificada";

            detalle.textContent =
                `${pieza.codigo} - ${pieza.nombre}`;


            boton.disabled = false;

            boton.textContent =
                "Escanear otra pieza";


        } catch (error) {

            idPiezaSeleccionada = null;

            console.error(
                "Error consultando la pieza:",
                error
            );


            confirmacion.classList.add(
                "hidden"
            );


            piezaResultado.classList.add(
                "hidden"
            );


            estado.textContent =
                "QR no reconocido";

            detalle.textContent =
                "El código escaneado no corresponde a una pieza registrada.";


            punto.classList.remove(
                "bg-green-500",
                "bg-violet-500"
            );

            punto.classList.add(
                "bg-red-500"
            );


            boton.disabled = false;

            boton.textContent =
                "Intentar nuevamente";
        }
    }




    /*
 * =========================================================
 * INICIAR INSPECCIÓN
 * =========================================================
 */

    btnIniciarInspeccion.addEventListener(
        "click",
        () => {

            if (!idPiezaSeleccionada) {

                console.error(
                    "No hay una pieza seleccionada"
                );

                return;
            }

            window.location.href =
                `/inspecciones/nueva/${idPiezaSeleccionada}`;
        }
    );


    /*
     * =========================================================
     * BOTÓN
     * =========================================================
     */

    boton.addEventListener(
        "click",
        () => {

            if (escaneando) {

                detenerCamara();

                estado.textContent =
                    "Escaneo detenido";

                detalle.textContent =
                    "Presiona el botón para volver a escanear.";

                return;
            }


            iniciarCamara();
        }
    );

});