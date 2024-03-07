import React from "react";
import "../styling/sidebar.css";
import "monday-ui-react-core/tokens";
import { Box, Menu, MenuItem, Flex } from "monday-ui-react-core";

const Sidebar = () => {
  return (
    <div className="sidebar-wrapper">
      <Box className="sidebar-menu-box">
        <Flex justify="Center" gap={10}>
          <Menu>
            <MenuItem title="Projects" />
            <MenuItem title="Projects Managers" />
            <MenuItem title="Employees" />
          </Menu>
        </Flex>
      </Box>
    </div>
  );
};

export default Sidebar;
