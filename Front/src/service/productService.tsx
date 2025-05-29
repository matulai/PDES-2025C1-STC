import axios from "axios";

const API_URL = import.meta.env.VITE_API_URL;

function getProductsByKeyword(filters: { text: string; category?: string }) {
  return axios.get(`${API_URL}/products/search`, {
    params: filters,
  });
}

function getProductById(id: string) {
  return axios.get(`${API_URL}/products/${id}`);
}

export { getProductById, getProductsByKeyword };
