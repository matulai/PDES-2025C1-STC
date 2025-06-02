function calculateProductPrice(mlaId: String, name: String) {
  const initialPrice = Number(mlaId.slice(3));
  const result = Math.floor(initialPrice / name.length);
  return result.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
}

export { calculateProductPrice };
