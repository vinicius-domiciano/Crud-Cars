import React from "react";
import { Navigate, Route, Routes } from "react-router-dom";
import Company from "./pages/Company";

import Home from "./pages/Home";

export default function Router() {
    return (
        <Routes>
            <Route path="/homepage" element={<Home />} />
            <Route path="/company" element={<Company />} />
            <Route path="*" element={<Navigate to="/homepage" replace />} />
        </Routes>
    );
}
