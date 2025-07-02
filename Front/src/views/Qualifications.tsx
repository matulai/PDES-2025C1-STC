import type { Qualification, PaginationElementDto } from "@/types";
import { allUsersQualifications } from "@/service/adminService";
import { Spinner, PaginationNav } from "@/components";
import { useState, useEffect } from "react";
import { useSearchParams } from "react-router-dom";
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
      })
      .finally(() => {
        setIsLoading(false);
      });
  }, []);

  if (isLoading) {
    return <Spinner />;
  }

  return (
    <>
      <h1 className="items-title">
        <strong style={{ fontWeight: "600" }}>Todas las calificaciones</strong>
      </h1>
      <div className="items">
        <div className="items-content-column">
          {paginationUsersQualifications?.data.map(
            (userQualifications, index) => (
              <div key={index} className="items-content-item">
                <p>
                  <b style={{ fontWeight: "600" }}>
                    {userQualifications.userName}
                  </b>{" "}
                  hizo un comentario en el producto:{" "}
                  {userQualifications.productName}
                </p>
                <p>comentario: "{userQualifications.comment}"</p>
                <p>puntuación: {userQualifications.score}</p>
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
