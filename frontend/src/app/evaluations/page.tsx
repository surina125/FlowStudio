"use client";

import { useRouter } from 'next/navigation';
import React, { useState } from "react";
import ChatbotCard from "@/components/chatbot/ChatbotCard";

interface Chatbot {
  id: number;
  title: string;
  description: string;
  category: string;
  iconId: number;
}

const chatbots: Chatbot[] = [
  {
    id: 1,
    title: "Workflow Planning Assistant",
    description: "An assistant that helps you plan and select the right node for a workflow (v0.6.0).",
    category: "교육",
    iconId: 1,
  },
  {
    id: 2,
    title: "Financial Advisor Bot",
    description: "Provides insights and suggestions for better financial planning (v1.2.3).",
    category: "금융",
    iconId: 1,
  },
  {
    id: 3,
    title: "Health Tracker Assistant",
    description: "Tracks your daily health metrics and offers tips to improve your well-being (v2.0.1).",
    category: "헬스케어",
    iconId: 1,
  },
  {
    id: 4,
    title: "E-Commerce Helper",
    description: "Assists in finding the best deals and manages your online shopping lists (v1.0.5).",
    category: "전자상거래",
    iconId: 1,
  },
  {
    id: 5,
    title: "Travel Itinerary Planner",
    description: "Helps you create and organize your travel plans with ease (v0.8.7).",
    category: "여행",
    iconId: 1,
  },
];

const Page = () => {
  const router = useRouter();
  const categories = [
    "모든 챗봇",
    "금융",
    "헬스케어",
    "전자상거래",
    "여행",
    "교육",
    "엔터테이먼트",
    "기타",
  ];

  const [selectedCategory, setSelectedCategory] = useState<string>("모든 챗봇");

  const handleCategoryClick = (label: string) => {
    setSelectedCategory(label);
  };

  const filteredChatbots = selectedCategory === "모든 챗봇"
    ? chatbots
    : chatbots.filter((chatbot) => chatbot.category === selectedCategory);

  const handleEvaluationClick = () => {
    router.push('/evaluation');
  };

  const handleResultClick = (id: number) => {
    router.push(`/evaluation/${id}/result`);
  };

  return (
    <div className="px-12 py-10">
      <div className="flex items-center mb-4">
        <p className="text-[22px] font-semibold mr-6">테스트 결과 확인</p>
          <button
            onClick={() => handleEvaluationClick()}
            className="py-2 px-4 text-[14px] bg-[#9A75BF] text-white rounded-lg"
          >
            챗봇 평가하기
          </button>
      </div>

      <div className="flex items-center mb-8">
        {categories.map((label) => (
          <button
            key={label}
            onClick={() => handleCategoryClick(label)}
            className={`mr-6 ${selectedCategory === label ? "font-semibold" : "text-gray-600"}`}
          >
            {label}
          </button>
        ))}
      </div>

      <div className="flex flex-col gap-1">
        {filteredChatbots.map((chatbot) => (
          <ChatbotCard
            key={chatbot.id}
            title={chatbot.title}
            description={chatbot.description}
            buttonText="결과 확인하기"
            onButtonClick={() => handleResultClick(chatbot.id)}
          />
        ))}
      </div>
    </div>
  );
}

export default Page;
