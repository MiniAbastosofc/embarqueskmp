import { Link, Outlet, useLocation, useNavigate } from "react-router-dom";
import { useAuthStore } from "../store/authStore";
import {
  HandCoins,
  ReceiptText,
  LogOut,
  Hotel,
  Calendar,
  X,
  Menu,
} from "lucide-react";
import { useState } from "react";

export function AdminLayout() {
  const username = useAuthStore((state) => state.username);
  const logout = useAuthStore((state) => state.logout);
  const location = useLocation();
  const navigate = useNavigate();
  const [menuAbierto, setMenuAbierto] = useState(false);

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  // Configuración de los botones de navegación
  const menuItems = [
    { name: "Reservas", path: "/reservas", icon: Calendar },
    { name: "Pagos", path: "/pagos", icon: HandCoins },
    { name: "Cortes", path: "/cortes", icon: ReceiptText },
  ];

  return (
    <div className="w-screen h-screen flex flex-col md:flex-row bg-tertiary font-body text-neutral overflow-hidden relative">
      {/* NAVBAR SUPERIOR - SOLO PARA MOBILE */}
      <header className="md:hidden w-full bg-primary text-white p-4 flex items-center justify-between shadow-md z-40">
        <div className="flex items-center gap-3">
          <Hotel className="text-secondary w-6 h-6" />
          <h1 className="font-headline text-lg font-bold tracking-wide">
            Casa Alcatraz
          </h1>
        </div>
        <button
          onClick={() => setMenuAbierto(true)}
          className="p-2 hover:bg-white/10 rounded-lg transition-colors"
        >
          <Menu className="w-6 h-6 text-secondary" />
        </button>
      </header>

      {/* OVERLAY FONDO - Cierra el menú mobile al hacer click afuera */}
      {menuAbierto && (
        <div
          className="md:hidden fixed inset-0 bg-black/50 z-40 transition-opacity"
          onClick={() => setMenuAbierto(false)}
        />
      )}

      {/* SIDEBAR - RESPONSIVE (Fijo en desktop, deslizable en mobile) */}
      <aside
        className={`
        fixed md:static top-0 left-0 z-50
        w-64 h-full bg-primary flex flex-col justify-between p-5 text-white shadow-xl
        transform transition-transform duration-300 ease-in-out
        ${menuAbierto ? "translate-x-0" : "-translate-x-full"} 
        md:translate-x-0
      `}
      >
        <div>
          {/* Encabezado Sidebar (Con botón de cerrar para mobile) */}
          <div className="flex items-center justify-between pb-6 border-b border-white/10 mb-6">
            <div className="flex items-center gap-3">
              <Hotel className="text-secondary w-8 h-8" />
              <h1 className="font-headline text-xl font-bold tracking-wide">
                Casa Alcatraz
              </h1>
            </div>
            {/* Botón cerrar sólo visible en mobile */}
            <button
              onClick={() => setMenuAbierto(false)}
              className="md:hidden p-1 hover:bg-white/10 rounded-lg"
            >
              <X className="w-6 h-6 text-secondary" />
            </button>
          </div>

          {/* Menú de Opciones */}
          <nav className="space-y-2">
            {menuItems.map((item) => {
              const Icon = item.icon;
              const isActive = location.pathname === item.path;

              return (
                <Link
                  key={item.path}
                  to={item.path}
                  onClick={() => setMenuAbierto(false)} // Cierra el menú al navegar en mobile
                  className={`flex items-center gap-3 px-4 py-3 rounded-xl transition-all font-medium text-sm group border-l-4 relative ${
                    isActive
                      ? "bg-white/10 text-secondary font-bold border-secondary"
                      : "hover:bg-white/5 text-white/80 hover:text-white border-transparent"
                  }`}
                >
                  {isActive && (
                    <span className="absolute left-0 top-0 h-full w-1 bg-secondary rounded-r" />
                  )}

                  <Icon
                    className={`w-5 h-5 transition-transform group-hover:scale-110 ${
                      isActive ? "text-secondary" : "text-secondary/60"
                    }`}
                  />
                  {item.name}
                </Link>
              );
            })}
          </nav>
        </div>

        {/* Sección de Usuario y Cerrar Sesión */}
        <div className="pt-4 border-t border-white/10 flex flex-col gap-3">
          <div className="px-2">
            <p className="text-xs opacity-60 uppercase font-semibold tracking-wider">
              Usuario activo
            </p>
            <p className="text-sm font-medium text-secondary truncate">
              {username || "Administrador"}
            </p>
          </div>

          <button
            onClick={handleLogout}
            className="flex items-center gap-3 w-full px-4 py-3 text-sm font-medium bg-red-500/10 hover:bg-red-500 text-red-400 hover:text-white rounded-xl transition-colors group"
          >
            <LogOut className="w-5 h-5 transform group-hover:-translate-x-1 transition-transform" />
            Cerrar Sesión
          </button>
        </div>
      </aside>

      {/* CONTENEDOR PRINCIPAL DE PÁGINAS */}
      <main className="flex-1 h-full overflow-y-auto p-4 md:p-8">
        <div className="max-w-7xl mx-auto">
          <Outlet />
        </div>
      </main>
    </div>
  );
}
