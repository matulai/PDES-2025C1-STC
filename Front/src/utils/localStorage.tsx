const addToLocalStorage = (key: string, value: any) => {
  localStorage.setItem(key, JSON.stringify(value));
};

const getFromLocalStorage = (key: string) => {
  const value = localStorage.getItem(key);
  return value ? JSON.parse(value) : null;
};

const clearLocalStorage = () => {
  localStorage.clear();
};

export { addToLocalStorage, getFromLocalStorage, clearLocalStorage };
