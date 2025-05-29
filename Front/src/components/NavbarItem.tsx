import { Link, useLocation } from "react-router-dom";
import "@/styles/NavbarItem.css";

interface NavbarItemProps {
  label: string;
  pathname: string;
}

const NavbarItem = ({ label, pathname }: NavbarItemProps) => {
  const location = useLocation();
  const isActive = location.pathname === pathname;
  return (
    <Link
      to={pathname}
      className={`navbar-link ${isActive ? "navbar-link-active" : "navbar-link-inactive"}`}
    >
      {label}
    </Link>
  );
};

export default NavbarItem;
