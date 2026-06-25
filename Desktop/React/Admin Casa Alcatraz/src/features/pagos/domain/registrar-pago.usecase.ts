import { type PagoFormValues } from "../presentation/hooks/useFormularioPagos";
import { ApiPagoRepository } from "../infrastructure/api-pago.repository";

export class RegistrarPagoUseCase {
  // 1. Declaras la propiedad explícitamente fuera del constructor
  private readonly repository: ApiPagoRepository;

  // 2. Asignas el valor de forma tradicional que TypeScript sí puede "borrar" al compilar
  constructor(repository: ApiPagoRepository) {
    this.repository = repository;
  }

  async execute(pago: PagoFormValues): Promise<void> {
    return await this.repository.registrar(pago);
  }
}
