import { useState, useRef, useEffect } from "react";
import { Calendar as CalendarIcon } from "lucide-react";
import { DayPicker, type DateRange } from "react-day-picker";
import { format } from "date-fns";
import { es } from "date-fns/locale";
import "react-day-picker/dist/style.css";
import { useCalendarioOcupacion } from "../hooks/useCalendarioOcupacion";

interface DateRangePickerProps {
  onRangeChange?: (range: DateRange | undefined) => void;
}

export function DateRangePicker({ onRangeChange }: DateRangePickerProps) {
  // 1. Cargamos los días ocupados desde tu arquitectura limpia
  const { diasOcupados, loading } = useCalendarioOcupacion();
  const [range, setRange] = useState<DateRange | undefined>();
  const [isOpen, setIsOpen] = useState(false);
  const containerRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    function handleClickOutside(event: MouseEvent) {
      if (
        containerRef.current &&
        !containerRef.current.contains(event.target as Node)
      ) {
        setIsOpen(false);
      }
    }
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  const handleSelect = (newRange: DateRange | undefined) => {
    setRange(newRange);
    if (onRangeChange) {
      onRangeChange(newRange);
    }
  };

  let displayText = loading
    ? "Cargando disponibilidad..."
    : "Seleccione el rango de fechas";

  if (range?.from) {
    if (range.to) {
      displayText = `${format(range.from, "dd MMM", { locale: es })} - ${format(range.to, "dd MMM yyyy", { locale: es })}`;
    } else {
      displayText = format(range.from, "dd MMM yyyy", { locale: es });
    }
  }

  // Obtenemos el día de hoy con hora en ceros para bloquear el pasado de forma exacta
  const hoy = new Date();
  hoy.setHours(0, 0, 0, 0);

  return (
    <div className="w-full relative" ref={containerRef}>
      <button
        type="button"
        disabled={loading}
        onClick={() => setIsOpen(!isOpen)}
        className="w-full rounded-md p-1 flex items-center gap-2 bg-transparent text-left cursor-pointer focus:outline-none disabled:opacity-50 disabled:cursor-not-allowed"
      >
        <CalendarIcon className="text-secondary w-5 h-5 flex-shrink-0" />
        <span
          className={`flex-1 font-body text-sm truncate ${range?.from ? "text-neutral" : "text-gray-400"}`}
        >
          {displayText}
        </span>
      </button>

      {isOpen && (
        <div className="absolute z-50 mt-2 p-4 bg-surfacecontainer border border-tertiary shadow-2xl rounded-xl left-0 md:left-auto">
          <DayPicker
            mode="range"
            selected={range}
            onSelect={handleSelect}
            locale={es}
            // 2. RESTRINGIMOS LOS DÍAS OCUPADOS Y LOS DÍAS PASADOS
            disabled={[...diasOcupados, { before: hoy }]}
            modifiersClassNames={{
              selected:
                "bg-primary text-white rounded-none first:rounded-l-md last:rounded-r-md",
              range_start: "bg-secondary text-primary font-bold rounded-l-md!",
              range_end: "bg-secondary text-primary font-bold rounded-r-md!",
              range_middle: "bg-primary/10 text-primary font-medium",
              today: "text-secondary font-bold underline",
              // 3. AGREGAMOS EL ESTILO ESTÉTICO DE BLOQUEO USANDO TU COLOR OHSONNY
              disabled:
                "bg-ohsunny/20 text-neutral/30 line-through cursor-not-allowed opacity-60 rounded-md",
            }}
            components={{
              DayButton: ({ day, ...props }) => {
                return (
                  <button
                    {...props}
                    className={`${props.className || ""} font-label transition-all duration-150`}
                  />
                );
              },
            }}
          />
        </div>
      )}
    </div>
  );
}
