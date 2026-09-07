package com.scannercp.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class QrService {

    /*
     * =========================================================
     * GENERAR CÓDIGO QR EN FORMATO PNG
     * =========================================================
     */

    public byte[] generarQrPng(String contenido)
            throws WriterException, IOException {

        if (contenido == null || contenido.isBlank()) {
            throw new IllegalArgumentException(
                    "El contenido del código QR es obligatorio"
            );
        }


        QRCodeWriter qrCodeWriter =
                new QRCodeWriter();


        BitMatrix bitMatrix =
                qrCodeWriter.encode(
                        contenido,
                        BarcodeFormat.QR_CODE,
                        400,
                        400
                );


        BufferedImage imagen =
                MatrixToImageWriter.toBufferedImage(
                        bitMatrix
                );


        ByteArrayOutputStream salida =
                new ByteArrayOutputStream();


        ImageIO.write(
                imagen,
                "PNG",
                salida
        );


        return salida.toByteArray();
    }
}