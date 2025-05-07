import axios from "axios";

const API_URL = import.meta.env.VITE_API_URL;
const PUBLIC_API_URL = import.meta.env.VITE_PUBLIC_API_URL;

/********************* Private requests *********************/

function getProductsByKeyword(query: string, domain_id?: string) {
  const params: Record<string, string> = { q: query };
  if (domain_id) params.domain_id = domain_id;

  return axios.get(`${API_URL}/products/search`, { params });
}

function getProductById(id: string) {
  return axios.get(`${API_URL}/products/${id}`);
}

/********************* Public requests *********************/

function getCategoryById(id: string) {
  return axios.get(`${PUBLIC_API_URL}/categories/${id}`);
}

export { getProductsByKeyword, getProductById, getCategoryById };
