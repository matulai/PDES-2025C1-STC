import "@/styles/LoginCard.css";

const LoginCard = () => {
  return (
    <section className="login-card-container">
      <h2 className="login-card-container-title">Ingresar</h2>
      <form action="#" method="post" className="login-card-container-form">
        <div className="login-card-container-form-inputs">
          <label htmlFor="username" style={{ display: "none" }}>
            Username
          </label>
          <input
            type="text"
            id="username"
            placeholder="Nombre"
            autoComplete="off"
            className="login-card-container-form-inputs-input"
          />
          <label htmlFor="password" style={{ display: "none" }}>
            Password
          </label>
          <input
            type="password"
            id="password"
            placeholder="Contraseña"
            autoComplete="off"
            className="login-card-container-form-inputs-input"
          />
        </div>
        <button type="submit" className="login-card-container-form-button">
          Ingresar
        </button>
      </form>
    </section>
  );
};

export default LoginCard;
