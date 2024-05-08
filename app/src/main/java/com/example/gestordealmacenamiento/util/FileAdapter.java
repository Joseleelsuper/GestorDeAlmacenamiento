package com.example.gestordealmacenamiento.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.example.gestordealmacenamiento.R;
import com.example.gestordealmacenamiento.app.FilesScreen;
import com.example.gestordealmacenamiento.app.HomeScreen;

import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 * Adaptador personalizado para la lista de archivos y carpetas.
 *
 * @author <a href="mailto:jgc1031@alu.ubu.es">José Gallardo Caballero</a>
 * @version 1.0
 * @serial 17/03/2024
 */
public class FileAdapter extends ArrayAdapter<File> {
    /**
     * Contexto de la aplicación.
     */
    private final Context context;
    /**
     * Lista de archivos y carpetas.
     */
    private final List<File> files;
    /**
     * Pantalla de archivos.
     */
    private final FilesScreen filesScreen;

    /**
     * Constructor de la clase.
     *
     * @param context     Contexto de la aplicación.
     * @param filesScreen Pantalla de archivos.
     * @param files       Lista de archivos y carpetas.
     */
    public FileAdapter(@NonNull Context context, FilesScreen filesScreen, List<File> files) {
        super(context, R.layout.list_item, files);
        this.context = context;
        this.files = files;
        this.filesScreen = filesScreen;
    }

    public FileAdapter(@NonNull Context context, List<File> files) {
        super(context, R.layout.list_item, files);
        this.context = context;
        this.files = files;
        this.filesScreen = null;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }

        File file = files.get(position);

        ImageView icon = convertView.findViewById(R.id.icon);
        TextView name = convertView.findViewById(R.id.name);
        ImageView iconMoreVert = convertView.findViewById(R.id.icon_morevert);

        if (file.isDirectory()) {
            icon.setImageResource(R.drawable.icon_folder);
        } else {
            icon.setImageResource(R.drawable.icon_file);
        }

        name.setText(file.getName());

        // Click listener para abrir el archivo o carpeta
        convertView.setOnClickListener(v -> {
            if (file.isDirectory()) {
                // Abre la carpeta
                openFolder(file);
            } else {
                // Abre el archivo
                openFile(file);
            }
        });

        // Long click listener para mostrar el diálogo de opciones
        convertView.setOnLongClickListener(v -> {
            showOptionsDialog(file, position);
            return true;
        });

        // Click listener para el icono de opciones
        iconMoreVert.setOnClickListener(v -> showOptionsDialog(file, position));

        return convertView;
    }

    /**
     * Muestra un diálogo de opciones para un archivo o carpeta.
     *
     * @param file     Archivo o carpeta.
     * @param position Posición del archivo o carpeta en la lista.
     */
    private void showOptionsDialog(File file, int position) {
        // Muestra un diálogo de opciones
        new AlertDialog.Builder(context)
                .setTitle("Elige una opción")
                .setItems(new String[]{"Abrir", "Renombrar", "Eliminar"}, (dialog, which) -> {
                    switch (which) {
                        case 0: // Abrir
                            if (file.isDirectory()) {
                                // Abre la carpeta
                                // Aquí necesitarás implementar la lógica para abrir la carpeta y mostrar su contenido
                                openFolder(file);
                            } else {
                                // Abre el archivo
                                openFile(file);
                            }
                            break;
                        case 1: // Renombrar
                            renameFile(file);
                            break;
                        case 2: // Eliminar
                            showDeleteConfirmationDialog(file, position);
                            break;
                    }
                })
                .show();
    }

    /**
     * Abre una carpeta.
     *
     * @param folder Carpeta a abrir.
     */
    private void openFolder(File folder) {
        if (filesScreen != null) {
            filesScreen.setCurrentDirectory(folder);
            filesScreen.displayFiles(0);
            HomeScreen.setRecentDirectory(folder);
        } else {
            ((HomeScreen) context).openFolder(folder);
        }
    }

    /**
     * Abre un archivo.
     *
     * @param file Archivo a abrir.
     */
    private void openFile(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri fileUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
        intent.setDataAndType(fileUri, "*/*");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(intent);
    }

    /**
     * Renombra un archivo.
     *
     * @param file Archivo a renombrar.
     */
    private void renameFile(@NonNull File file) {
        // Muestra un diálogo de entrada
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Nuevo nombre del archivo:");

        // Configura el campo de entrada
        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(file.getName());
        builder.setView(input);

        // Configura los botones
        builder.setPositiveButton("OK", (dialog, which) -> {
            String newName = input.getText().toString();

            // Comprueba que el nuevo nombre no esté vacío y que no exista otro archivo con el mismo nombre y extensión
            File newFile = new File(file.getParent(), newName);
            if (!newName.isEmpty() && !newFile.exists()) {
                // Renombra el archivo
                if (file.renameTo(newFile)) {
                    Toast.makeText(context, "Archivo renombrado con éxito.", Toast.LENGTH_SHORT).show();
                    files.remove(file);
                    files.add(newFile);
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Error al renombrar el archivo.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "El nombre del archivo no es válido.", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    /**
     * Elimina un archivo o carpeta y sus subcarpetas y archivos.
     *
     * @param fileOrDirectory Fichero a eliminar.
     */
    private void deleteFileOrDirectory(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : Objects.requireNonNull(fileOrDirectory.listFiles()))
                deleteFileOrDirectory(child);

        fileOrDirectory.delete();
    }

    /**
     * Muestra un diálogo de confirmación para eliminar un archivo o carpeta.
     *
     * @param file     Archivo o carpeta a eliminar.
     * @param position Posición del archivo o carpeta en la lista.
     */
    private void showDeleteConfirmationDialog(File file, int position) {
        String message;
        if (file.isDirectory() && Objects.requireNonNull(file.listFiles()).length > 0) {
            message = "Estás a punto de eliminar una carpeta con ficheros. ¿Deseas continuar?";
        } else {
            message = "Estás a punto de eliminar " + (file.isDirectory() ? "una carpeta" : "un archivo") + ". ¿Deseas continuar?";
        }

        new AlertDialog.Builder(context)
                .setTitle("Confirmar eliminación")
                .setMessage(message)
                .setPositiveButton("Sí", (dialog, which) -> {
                    deleteFileOrDirectory(file);
                    files.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(context, file.isDirectory() ? "Carpeta eliminada" : "Arhivo eliminado" + " con éxito.", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", null)
                .show();
    }
}
