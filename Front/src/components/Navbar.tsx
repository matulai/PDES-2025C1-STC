import { Link, useLocation } from "react-router-dom";

const navLinksNoRegistered = [
  { label: "Registrarse", pathname: "/register" },
  { label: "Ingresar", pathname: "/login" },
  { label: "Mis favoritos", pathname: "/favorites" },
  { label: "Mis compras", pathname: "/purchases" },
];

const navLinksRegistered = [
  { label: "Mi perfil", pathname: "/profile" },
  { label: "Mis favoritos", pathname: "/favorites" },
  { label: "Mis compras", pathname: "/purchases" },
];

const Navbar = () => {
  const location = useLocation();

  return (
    <nav>
      {navLinksNoRegistered.map(link => (
        <Link key={link.label} to={link.pathname}>
          {link.label}
        </Link>
      ))}
    </nav>
  );
};

export default Navbar;
