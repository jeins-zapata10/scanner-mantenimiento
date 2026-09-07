document.addEventListener("DOMContentLoaded", () => {

    const boton = document.getElementById("btnIniciarScanner");
    const video = document.getElementById("cameraPreview");
    const placeholder = document.getElementById("scannerPlaceholder");

    const estado = document.getElementById("scannerEstado");
    const detalle = document.getElementById("scannerDetalle");
    const punto = document.getElementById("scannerEstadoPunto");

    let stream = null;
    let escaneando = false;
    let detector = null;
    let detectandoFrame = false;


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


    /*
     * =========================================================
     * QR DETECTADO
     * =========================================================
     */

    function qrDetectado(contenido) {

        detenerCamara();


        estado.textContent =
            "QR detectado";

        detalle.textContent =
            contenido;


        punto.classList.remove(
            "bg-gray-400",
            "bg-violet-500"
        );

        punto.classList.add(
            "bg-green-500"
        );


        console.log(
            "QR ScannerCP:",
            contenido
        );
    }


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