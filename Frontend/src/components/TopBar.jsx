import React from "react";
import { Flex, Box, Search, Avatar } from "monday-ui-react-core";
import "monday-ui-react-core/tokens";

const TopBar = () => {
  return (
    <div className="top-bar-wrapper">
      <Flex justify="SpaceBetween" className="bar-container">
        <Box className="box1">
          <Flex gap={8}>
            <Box className="logo-container">
              <img src="/images/CS.png" />
            </Box>

            <Box className="company-name-container">
              <label>Customer Support</label>
            </Box>
          </Flex>
        </Box>
        <Box className="box2">
          <Search placeholder="Search.." />
        </Box>
        <Box className="box3">
          <Flex justify="Center" gap={10}>
            <Avatar
              ariaLabel="Hadas Fahri"
              size="large"
              src="https://style.monday.com/static/media/person1.de30c8ee.png"
              type="img"
            />
            <Box>
              <Flex direction="Column">
                <label>name</label>
                <label>role</label>
              </Flex>
            </Box>
          </Flex>
        </Box>
      </Flex>
    </div>
  );
};

export default TopBar;
