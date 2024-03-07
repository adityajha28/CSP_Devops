import React from "react";
import TopBar from "./components/TopBar";
import Sidebar from "./components/Sidebar";
import Content from "./components/Content";
import Project from "./components/Project";
import "./styling/layout.css";
import { Box, Flex } from "monday-ui-react-core";

const Layout = () => {
  return (
    <div>
      <Flex
        className="layout-wrapper"
        direction="Column"
        gap={20}
        justify="SpaceBetween"
      >
        <Box className="top-bar-container">
          <TopBar />
        </Box>

        <Box className="main-content-wrapper">
          <Flex justify="SpaceBetween">
            <Box className="sidebar-container">
              <Sidebar />
            </Box>
            <Box className="content-container">
              {/* <Content /> */}
              <Project />
            </Box>
          </Flex>
        </Box>
      </Flex>
    </div>
  );
};

export default Layout;
