import { Product } from "@/types";

interface ExternalProduct {
  id: string;
  title: string;
  thumbnail: string;
  domain_id?: string;
  attributes?: { id: string; value_name: string }[];
  [key: string]: any; // ignora los demas campos
}

const mapToProduct = (external: ExternalProduct): Product => {
  return {
    MLId: external.id,
    name: external.name,
    imageUrl: external.pictures.url,
    description: external?.short_description.content,
    domain_id: external.domain_id,
  };
};

export { mapToProduct };
