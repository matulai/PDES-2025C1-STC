import type { Qualification, PaginationElementDto } from "@/types";
import { allUsersQualifications } from "@/service/adminService";
import { Spinner, PaginationNav } from "@/components";
import { useState, useEffect } from "react";
import { useSearchParams } from "react-router-dom";
import { toast } from "react-hot-toast";
import "@/styles/Items.css";

const Qualifications = () => {
  const [searchParams] = useSearchParams();
  const [paginationUsersQualifications, setPaginationUsersQualifications] =
    useState<PaginationElementDto<Qualification>>();
  const [isLoading, setIsLoading] = useState(true);

  const page = Number(searchParams.get("page"));

  useEffect(() => {
    allUsersQualifications(page)
      .then(res => {
        setPaginationUsersQualifications(res);
      })
      .catch(error => {
        console.log(error);
        toast.error("Error al obtener calificaciones");
      })
      .finally(() => {
        setIsLoading(false);
      });
  }, []);

  if (isLoading) {
    return <Spinner classType="spinner-fullscreen" />;
  }

  return (
    <>
      <h1 className="items-title">
        <strong style={{ fontWeight: "600" }}>Todas las calificaciones</strong>
      </h1>
      <div className="items">
        <div className="items-content-column">
          {paginationUsersQualifications?.data.map(
            (userQualification, index) => (
              <div key={index} className="items-content-item">
                <p>
                  <b style={{ fontWeight: "600" }}>
                    {userQualification.userName}
                  </b>{" "}
                  hizo un comentario en el producto:{" "}
                  {userQualification.productName}
                </p>
                <p>comentario: "{userQualification.comment}"</p>
                <p>puntuación: {userQualification.score}</p>
              </div>
            )
          )}
        </div>
      </div>
      <PaginationNav pagination={paginationUsersQualifications!.pagination} />
    </>
  );
};

export default Qualifications;
