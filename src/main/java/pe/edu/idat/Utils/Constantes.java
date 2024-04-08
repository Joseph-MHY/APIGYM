package pe.edu.idat.Utils;

import java.util.HashMap;
import java.util.Map;

public class Constantes {
    public static final String DELETE_USER = "Usuario Eliminado Correctamente";

    public static final String UPDATE_USER = "Usuario Actualizado Correctamente";

    public static final String CREATED_USER = "Usuario Creado Correctamente";

    public static final String ERROR_USER = "Usuario ya existente";

    public static Object returnMessageAndObject(String message, Object object) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("objeto", object);
        return response;
    }

    public static Object returnMessage(String message) {
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return response;
    }
}
