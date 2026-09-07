package com.scannercp.service;

import com.scannercp.dto.MaquinaRegistroForm;
import com.scannercp.model.Maquina;
import com.scannercp.repository.MaquinaRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
public class MaquinaService {

        private final MaquinaRepository maquinaRepository;

        public MaquinaService(MaquinaRepository maquinaRepository) {
                this.maquinaRepository = maquinaRepository;
        }

        /*
         * =========================================================
         * LISTAR MÁQUINAS
         * =========================================================
         */

        public List<Maquina> listarMaquinas() {
                return maquinaRepository.findAll();
        }

        public Maquina buscarPorId(Long idMaquina) {

                return maquinaRepository.findById(idMaquina)
                        .orElseThrow(() -> new IllegalArgumentException("La máquina no existe"));
        }

        /*
         * =========================================================
         * CREAR MÁQUINA
         * =========================================================
         */

        @Transactional
        public Maquina crearMaquina(MaquinaRegistroForm formulario) {

                /*
                 * Normalizar datos principales
                 */

                String codigoNormalizado = formulario.getCodigo()
                                .trim()
                                .toUpperCase(Locale.ROOT);

                String numeroSerieNormalizado = normalizarOpcional(formulario.getNumeroSerie());

                /*
                 * Validar código duplicado
                 */

                if (maquinaRepository.existsByCodigo(codigoNormalizado)) {

                        throw new IllegalArgumentException(
                                        "Ya existe una máquina registrada con ese código");
                }

                /*
                 * Validar número de serie duplicado.
                 *
                 * El número de serie es opcional,
                 * por eso solo se valida cuando existe.
                 */

                if (numeroSerieNormalizado != null
                                && maquinaRepository.existsByNumeroSerie(numeroSerieNormalizado)) {

                        throw new IllegalArgumentException(
                                        "Ya existe una máquina registrada con ese número de serie");
                }

                /*
                 * Crear entidad
                 */

                Maquina maquina = new Maquina();

                maquina.setCodigo(codigoNormalizado);

                maquina.setNombre(
                                formulario.getNombre().trim());

                maquina.setArea(
                                normalizarOpcional(formulario.getArea()));

                maquina.setUbicacion(
                                normalizarOpcional(formulario.getUbicacion()));

                maquina.setFabricante(
                                normalizarOpcional(formulario.getFabricante()));

                maquina.setModelo(
                                normalizarOpcional(formulario.getModelo()));

                maquina.setNumeroSerie(
                                numeroSerieNormalizado);

                maquina.setDescripcion(
                                normalizarOpcional(formulario.getDescripcion()));

                maquina.setEstado(
                                formulario.getEstado()
                                                .trim()
                                                .toUpperCase(Locale.ROOT));

                /*
                 * Guardar en MySQL
                 */

                return maquinaRepository.save(maquina);
        }

        /*
         * =========================================================
         * NORMALIZAR CAMPOS OPCIONALES
         * =========================================================
         */

        private String normalizarOpcional(String valor) {

                if (valor == null) {
                        return null;
                }

                String valorNormalizado = valor.trim();

                if (valorNormalizado.isEmpty()) {
                        return null;
                }

                return valorNormalizado;
        }
}