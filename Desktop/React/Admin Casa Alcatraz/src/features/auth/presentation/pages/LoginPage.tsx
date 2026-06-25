import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { loginSchema, type LoginFormData } from "../../domain/login.schema";
import { useAuth } from "../hooks/useAuth";

export function LoginPage() {
  const { signIn, isLoading, error } = useAuth();

  // Configuración de React Hook Form conectado a Zod
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<LoginFormData>({
    resolver: zodResolver(loginSchema),
  });

  return (
    <div className="w-screen h-screen flex justify-center items-center bg-tertiary font-body">
      <div className="w-full max-w-md bg-white p-8 rounded-2xl shadow-xl border border-gray-100 mx-4">
        <div className="text-center mb-8">
          <h1 className="font-headline text-3xl font-bold text-primary tracking-wide">
            Casa Alcatraz
          </h1>
          <p className="text-neutral text-sm mt-2 opacity-70">
            Panel Administrativo de Control
          </p>
        </div>

        {/* Error global que regresa tu API de Axios */}
        {error && (
          <div className="bg-red-50 text-red-600 text-sm p-3 rounded-lg mb-4 text-center border border-red-100">
            {error}
          </div>
        )}

        <form
          onSubmit={handleSubmit((data) => signIn(data.user, data.password))}
          className="space-y-5"
        >
          <div>
            <label className="block text-xs font-semibold uppercase tracking-wider text-neutral mb-2">
              Nombre de Usuario
            </label>
            <input
              type="text"
              {...register("user")}
              disabled={isLoading}
              className={`w-full px-4 py-3 bg-tertiary border rounded-xl focus:outline-none transition-colors text-neutral ${
                errors.user
                  ? "border-red-500 focus:border-red-500"
                  : "border-transparent focus:border-secondary"
              }`}
              placeholder="Usuario Merksyst"
            />
            {errors.user && (
              <p className="text-red-500 text-xs mt-1 font-medium">
                {errors.user.message}
              </p>
            )}
          </div>

          <div>
            <label className="block text-xs font-semibold uppercase tracking-wider text-neutral mb-2">
              Contraseña
            </label>
            <input
              type="password"
              {...register("password")}
              disabled={isLoading}
              className={`w-full px-4 py-3 bg-tertiary border rounded-xl focus:outline-none transition-colors text-neutral ${
                errors.password
                  ? "border-red-500 focus:border-red-500"
                  : "border-transparent focus:border-secondary"
              }`}
              placeholder="••••••••"
            />
            {errors.password && (
              <p className="text-red-500 text-xs mt-1 font-medium">
                {errors.password.message}
              </p>
            )}
          </div>

          <button
            type="submit"
            disabled={isLoading}
            className="w-full py-3.5 bg-primary text-white font-semibold rounded-xl shadow-md hover:bg-opacity-95 transform active:scale-[0.98] transition-all tracking-wide disabled:bg-gray-400 disabled:pointer-events-none"
          >
            {isLoading ? "Autenticando..." : "Iniciar Sesión"}
          </button>
        </form>
      </div>
    </div>
  );
}
