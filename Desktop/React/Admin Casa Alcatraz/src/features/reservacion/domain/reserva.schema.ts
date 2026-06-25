import { z } from "zod";

export const reservaSchema = z
  .object({
    nombreHuesped: z
      .string()
      .min(3, "El nombre debe tener al menos 3 caracteres"),
    apellidoHuesped: z
      .string()
      .min(3, "El apellido debe tener al menos 3 caracteres"),
    origenReserva: z
      .string()
      .min(1, "El origen de la reservación es obligatorio"),
    nuevoOrigenNombre: z.string().optional(), // <-- Declaramos el campo como opcional de inicio
    diasHospedaje: z.number().min(1, "Debe hospedarse al menos 1 día"),
    montoTotal: z.number().min(1, "El monto total no puede ser 0 o negativo"),
    fechaEntrada: z.string().min(1, "La fecha de entrada es obligatoria"),
    fechaSalida: z.string().min(1, "La fecha de salida es obligatoria"),
    telefono: z
      .string()
      .min(10, "El teléfono debe tener al menos 10 dígitos")
      .max(15, "El teléfono no puede tener más de 15 dígitos")
      .regex(/^[0-9]+$/, "El teléfono solo debe contener números"),
    huespedes: z
      .number()
      .int("El número de huéspedes debe ser un número entero")
      .min(1, "El número de huéspedes debe ser de al menos 1"),
    extras: z
      .array(
        z.object({
          concepto: z.string().min(1, "El concepto es obligatorio"),
          // 🚀 SOLUCIÓN COMPATIBLE CON EL BUILD: Pre-procesamos la entrada
          monto: z.preprocess(
            (val) => (val === "" ? undefined : Number(val)), // Si está vacío manda undefined, si no lo vuelve número
            z
              .number({ message: "El monto debe ser un número válido" })
              .min(1, "El monto debe ser mayor a 0"),
          ),
        }),
      )
      .optional(),
  })
  // 🧠 Lógica condicional: Validamos en tiempo real basándonos en la selección del usuario
  .superRefine((data, ctx) => {
    if (
      data.origenReserva === "OTRO" &&
      (!data.nuevoOrigenNombre || data.nuevoOrigenNombre.trim() === "")
    ) {
      ctx.addIssue({
        code: z.ZodIssueCode.custom,
        message: "Debes especificar el nombre del nuevo origen",
        path: ["nuevoOrigenNombre"], // <--- Apunta el error directamente a ese input
      });
    }
  });

// Tu tipo automático de TypeScript ahora incluirá "nuevoOrigenNombre" de forma nativa
export type ReservaFormData = z.infer<typeof reservaSchema>;
