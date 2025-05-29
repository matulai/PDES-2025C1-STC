import axios from "axios";
import { User } from "@/types";

const API_URL = import.meta.env.VITE_API_URL;

function login(name: string, password: string) {
  return axios.post<User>(`${API_URL}/auth/login`, {
    name: name,
    password: password,
  });
}

function register(name: string, password: string, role: string) {
  return axios.post<User>(`${API_URL}/auth/register`, {
    name: name,
    password: password,
    role: role,
  });
}

export { login, register };
