import React, { useState } from "react";
import {
  Box,
  Flex,
  TabList,
  Tab,
  TabPanel,
  TabPanels,
} from "monday-ui-react-core";
import "monday-ui-react-core/tokens";
import "../styling/project.css"
import ScopeAndStack from "./ScopeAndStack";
import EscalationMatrix from "./EscalationMatrix";
import PhaseMilestones from "./PhaseMilestones";
import SprintDetails from "./Sprintdetail";
import RiskProfiling from "./RiskProfiling";
import Stakeholders from "./Stakeholders";
import VersionHistory from "./VersionHistory";
import AuditHistory from "./AuditHistory";
import Overview from "./OverView";
import Api from "./Api";
const Project = () => {
  const [activeTab, setActiveTab] = useState(0);

  return (
    <div style={{marginTop:"20px"}}>
      <div className="save_button">
      <a href="http://localhost:8081/export/pdf"><button className="Export_button">Export As Pdf</button></a> 
        

      </div>
      <Box className="project-component-wrapper" >
        <Flex
          direction="Column"
          gap={15}
          className="project-component-flex"
          justify="Start"
          align="Start"
        >
          <Box className="project-tab-box">
            <TabList
              tabType="stretched"
              onTabChange={(tabId) => {
                setActiveTab(tabId);
              }}
            >
              <Tab>Project Overview</Tab>
              <Tab>Scope and Stack</Tab>
              <Tab>Escalation Matrix</Tab>
              <Tab>Phases</Tab>
              <Tab>Sprint Details</Tab>
              <Tab>Risk Profiling</Tab>
              <Tab>Stakeholders</Tab>
              <Tab>Version History</Tab>
              <Tab>Audit History</Tab>
            </TabList>
          </Box>

          <Box className="project-section-box">
            <TabPanels activeTabId={activeTab}>
              <TabPanel>
                <Overview/>
              </TabPanel>
              <TabPanel>
                <ScopeAndStack />
              </TabPanel>
              <TabPanel>
                <EscalationMatrix />
              </TabPanel>
              <TabPanel>
                <PhaseMilestones />
              </TabPanel>
              <TabPanel>
                <SprintDetails />
              </TabPanel>
              <TabPanel>
                <RiskProfiling/>
              </TabPanel>
              <TabPanel>
                <Stakeholders/>
              </TabPanel>
              <TabPanel>
                <VersionHistory/>
              </TabPanel>
              <TabPanel>
                <AuditHistory/>
              </TabPanel>
              {/* <TabPanel>
                <Project_Phases_Section />
              </TabPanel>
              <TabPanel>
                <Project_Sprint_Details_Section />
              </TabPanel>
              <TabPanel>
                <Project_Risk_Profiling_Section />
              </TabPanel>
              <TabPanel>
                <Project_Stakeholder_Section />
              </TabPanel>
              <TabPanel>
                <Project_Version_History_Section />
              </TabPanel>
              <TabPanel>
                <Project_Audit_History_Section />
              </TabPanel> */}
            </TabPanels>
          </Box>
        </Flex>
      </Box>
    </div>
  );
};

export default Project;
