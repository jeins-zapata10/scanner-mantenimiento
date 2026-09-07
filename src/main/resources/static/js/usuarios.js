document.addEventListener("DOMContentLoaded", () => {
  const buscarUsuario = document.getElementById("buscarUsuario");

  const filtroRol = document.getElementById("rol");

  const filtroEstado = document.getElementById("estado");

  const filasUsuarios = document.querySelectorAll(".usuario-fila");

  const sinResultados = document.getElementById("sinResultadosFiltro");

  function filtrarUsuarios() {
    const texto = buscarUsuario.value.trim().toLowerCase();

    const rol = filtroRol.value;

    const estado = filtroEstado.value;

    let cantidadVisibles = 0;

    filasUsuarios.forEach((fila) => {
      const nombres = (fila.dataset.nombres || "").toLowerCase();

      const correo = (fila.dataset.correo || "").toLowerCase();

      const codigo = (fila.dataset.codigo || "").toLowerCase();

      const rolUsuario = fila.dataset.rol || "";

      const estadoUsuario = fila.dataset.estado || "";

      /*
       * BÚSQUEDA POR:
       *
       * nombre
       * apellido
       * correo
       * código
       */

      const coincideBusqueda =
        texto === "" ||
        nombres.includes(texto) ||
        correo.includes(texto) ||
        codigo.includes(texto);

      /*
       * FILTRO POR ROL
       */

      const coincideRol = rol === "" || rolUsuario === rol;

      /*
       * FILTRO POR ESTADO
       */

      const coincideEstado = estado === "" || estadoUsuario === estado;

      /*
       * EL USUARIO DEBE CUMPLIR
       * TODOS LOS FILTROS
       */

      const mostrar = coincideBusqueda && coincideRol && coincideEstado;

      if (mostrar) {
        fila.style.display = "";

        cantidadVisibles++;
      } else {
        fila.style.display = "none";
      }
    });

    /*
     * MOSTRAR MENSAJE CUANDO
     * NO EXISTAN RESULTADOS
     */

    if (sinResultados) {
      sinResultados.style.display = cantidadVisibles === 0 ? "" : "none";
    }
  }

  /*
   * BUSCAR MIENTRAS ESCRIBIMOS
   */

  buscarUsuario.addEventListener("input", filtrarUsuarios);

  /*
   * FILTRAR AL CAMBIAR EL ROL
   */

  filtroRol.addEventListener("change", filtrarUsuarios);

  /*
   * FILTRAR AL CAMBIAR EL ESTADO
   */

  filtroEstado.addEventListener("change", filtrarUsuarios);
});
