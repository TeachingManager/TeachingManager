import React from "react";
import { Box } from "@mui/material";
import FeeChart from "../fee/FeeChart";
import DashBoard from "./DashBoard";
import './home-contents.css';
const HomeContents = ()=> {
    return(
        <div className="home-contents-container">
           <DashBoard/>
        </div>
    )
}

export default HomeContents;