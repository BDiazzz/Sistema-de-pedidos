package com.tpi.gpdrl.Menu.Service;

import java.io.FileInputStream;
import java.io.IOException;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import org.springframework.stereotype.Component;
import java.util.Collections;

@Component
public class GoogleDriverService {
    
    private Drive driveService;

    public GoogleDriverService() throws IOException {
        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream("src/main/resources/tpiproyect-5d1d12e816fc.json"))
                .createScoped(Collections.singleton(DriveScopes.DRIVE_FILE));
        driveService = new Drive.Builder(credential.getTransport(), credential.getJsonFactory(), credential)
                .setApplicationName("tpiproyect")
                .build();
    }

    public Drive getDriveService() {
        return driveService;
    }
    public void eliminarArchivo(String idArchivo) {
        try {
            Drive driveService = getDriveService(); // Implementa el cliente Drive
            driveService.files().delete(idArchivo).execute();
            System.out.println("Archivo eliminado exitosamente: " + idArchivo);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar archivo de Google Drive", e);
        }
    }
    
}
