import { ChevronLeftIcon } from "@/icons";
import { useRef } from "react";
import "@/styles/CarouselWrapper.css";

interface CarouselWrapperProps {
  children: React.ReactNode;
}

const CarouselWrapper = ({ children }: CarouselWrapperProps) => {
  const carouselContainerRef = useRef<HTMLDivElement>(null);

  const handleOnClickScroll = (direction: "left" | "right") => {
    const current = carouselContainerRef.current;
    if (!current) return;

    const scrollAmount = current.clientWidth + 24;

    current.scrollBy({
      left: direction === "left" ? -scrollAmount : scrollAmount,
      behavior: "smooth",
    });
  };

  return (
    <>
      <button
        className="scrollable-carousel-button scrollable-carousel-button-prev"
        onClick={() => handleOnClickScroll("left")}
      >
        <ChevronLeftIcon />
      </button>
      <div className="scrollable-carousel-container" ref={carouselContainerRef}>
        <div className="scrollable-carousel-container-content">{children}</div>
      </div>
      <button
        className="scrollable-carousel-button scrollable-carousel-button-next"
        onClick={() => handleOnClickScroll("right")}
      >
        <ChevronLeftIcon style={{ transform: "rotate(180deg)" }} />
      </button>
    </>
  );
};

export default CarouselWrapper;
