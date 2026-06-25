import { useEffect, useState } from "react"; // Importamos useState para manejar el spinner/bloqueo de botones
import { useForm, useFieldArray } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { format, differenceInDays } from "date-fns";
import {
  reservaSchema,
  // type ReservaFormData,
} from "../../domain/reserva.schema";
import { ReservaRepository } from "../../infrastructure/reservas.repository";
import { notify } from "../../../../core/components/notifierTostify";
import {
  OrigenReservaRepository,
  type OrigenReservaDTO,
} from "../../infrastructure/origenReserva.repository";

export const useReservasForm = (onSuccess?: () => void) => {
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [origenes, setOrigenes] = useState<OrigenReservaDTO[]>([]);

  const {
    register,
    handleSubmit,
    setValue,
    reset,
    watch,
    control,
    formState: { errors },
  } = useForm<any>({
    resolver: zodResolver(reservaSchema),
    defaultValues: {
      nombreHuesped: "",
      apellidoHuesped: "",
      telefono: "",
      huespedes: 1,
      origenReserva: "",
      nuevoOrigenNombre: "",
      diasHospedaje: 0,
      montoTotal: 0,
      fechaEntrada: "",
      fechaSalida: "",
      extras: [] as Array<{ concepto: string; monto: number }>,
    },
  });

  const { fields, append, remove } = useFieldArray({
    control,
    name: "extras",
  });

  useEffect(() => {
    const cargarOrigenes = async () => {
      try {
        const data = await OrigenReservaRepository.getAll();
        setOrigenes(data);
      } catch (error) {
        console.error("Error al cargar orígenes de reserva", error);
      }
    };
    cargarOrigenes();
  }, []);

  const origenSeleccionado = watch("origenReserva");
  const esOrigenNuevo = origenSeleccionado === "OTRO";

  const montoBaseInput = watch("montoTotal") || 0; // Lo que el usuario escribe en la casilla original
  const listaExtras = watch("extras") || [];

  const subtotalExtras = listaExtras.reduce((acc: number, current: any) => {
    const valor = current && current.monto ? Number(current.monto) : 0;
    return acc + (isNaN(valor) ? 0 : valor);
  }, 0);

  const granMontoTotalCalculado = Number(montoBaseInput) + subtotalExtras;

  const handleDateChange = (range: any) => {
    if (range?.from) {
      setValue("fechaEntrada", format(range.from, "yyyy-MM-dd"), {
        shouldValidate: true,
      });

      if (range.to) {
        setValue("fechaSalida", format(range.to, "yyyy-MM-dd"), {
          shouldValidate: true,
        });
        const dias = differenceInDays(range.to, range.from);
        setValue("diasHospedaje", dias, { shouldValidate: true });
      } else {
        setValue("fechaSalida", "");
        setValue("diasHospedaje", 0);
      }
    } else {
      setValue("fechaEntrada", "");
      setValue("fechaSalida", "");
      setValue("diasHospedaje", 0);
    }
  };

  // FUNCIÓN ASÍNCRONA QUE CONECTA CON LA API
  const onSubmit = async (data: any) => {
    setIsSubmitting(true);
    console.log(data, "Datos");
    
    data.montoTotal = granMontoTotalCalculado;
    console.log("Datos capturados en el formulario:", data);
    try {
      if (data.origenReserva !== "OTRO") {
        data.nuevoOrigenNombre = "";
      }

      await ReservaRepository.create(data);
      notify.success("¡La reserva se ha guardado correctamente!");
      reset();
      if (onSuccess) onSuccess();
    } catch (error: any) {
      notify.error(
        error.message || "Ocurrió un error al procesar la reservación",
      );
    } finally {
      setIsSubmitting(false);
    }
  };

  const handleClear = () => {
    reset();
  };

  return {
    register,
    handleSubmit,
    onSubmit,
    handleDateChange,
    handleClear,
    errors,
    isSubmitting,
    origenes,
    esOrigenNuevo,
    extrasFields: fields,
    agregarExtra: () => append({ concepto: "", monto: 0 }),
    eliminarExtra: (index: number) => remove(index),
    granMontoTotalCalculado,
  };
};
