import axios from "axios";

const API_URL = import.meta.env.VITE_API_URL;

function login(username: string, password: string) {
  return axios.post(`${API_URL}/auth/login`, { username, password });
}

function register(username: string, password: string, role: string) {
  return axios.post(`${API_URL}/auth/register`, { username, password, role });
}

export { login, register };
