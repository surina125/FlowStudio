interface PopularChatbotCardProps {
  title: string;
  description: string;
  buttonText?: string;
  onButtonClick?: () => void;
}

export default function PopularChatbotCard({
  title,
  description,
  buttonText,
  onButtonClick,
}: PopularChatbotCardProps) {
  return (
    <div className="w-full h-[204px] px-6 py-4 rounded-xl group cursor-pointer border-2 hover:border-[#9A75BF]">
      <div className="mb-3 flex items-center gap-2">
        <div className="mr-2 w-[36px] h-[36px] rounded-md bg-gray-200"></div>
        <p className="text-[18px]">{title}</p>
      </div>
      <div className="flex flex-col h-[122px] justify-between">
        <p className="text-[#333333]">{description}</p>

        {buttonText && onButtonClick && (
          <button
            onClick={onButtonClick}
            className="flex items-center justify-center p-2 bg-[#9A75BF] text-white text-[14px] rounded-lg opacity-0 group-hover:opacity-100 transition-opacity"
          >
            {buttonText}
          </button>
        )}
      </div>
    </div>
  );
}
