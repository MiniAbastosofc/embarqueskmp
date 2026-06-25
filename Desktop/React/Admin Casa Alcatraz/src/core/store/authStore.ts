import { create } from "zustand";
import { persist } from "zustand/middleware";

interface AuthState {
  token: string | null;
  username: string | null;
  roleId: number | null;
  userId: number | null;
  setSession: (
    token: string,
    username: string,
    roleId: number,
    userId: number,
  ) => void;
  logout: () => void;
}

export const useAuthStore = create<AuthState>()(
  persist(
    (set) => ({
      token: null,
      username: null,
      roleId: null,
      userId: null,

      setSession: (token, username, roleId, userId) => {
        set({ token, username, roleId, userId });
      },

      logout: () => {
        set({ token: null, username: null, roleId: null });
      },
    }),
    {
      name: "auth-storage",
    },
  ),
);
