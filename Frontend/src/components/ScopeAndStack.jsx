import React, { useEffect, useState } from "react";
import { Dropdown } from "monday-ui-react-core";
import "monday-ui-react-core/tokens";
import axios from "axios";
import './../styling/scope.css';

const ScopeAndStack = () => {
  const [projectDetails, setProjectDetails] = useState({});
  const [changesMade, setChangesMade] = useState(false);

  const handleSubmit = async () => {
    try {
      const { data } = await axios.post(
        "http://localhost:8000/project/project_details",
        {
          projectDetails,
        }
      );
      setChangesMade(false);
      console.log(data);
    } catch (error) {
      console.log(error);
    }
  };

  const handleInputChange = (e, field) => {
    console.log(e, field);
    const newProjectDetails = { ...projectDetails };
    if (field === "stack") {
      console.log(e);
      if (e == null) {
        delete newProjectDetails[field];
      } else {
        newProjectDetails[field] = {};
        newProjectDetails[field]["label"] = e.label;
        newProjectDetails[field]["value"] = e.value;
        console.log(newProjectDetails);
      }
    } else {
      newProjectDetails[field] = e.target.value;
    }

    console.log(newProjectDetails);
    setProjectDetails(newProjectDetails);
    setChangesMade(true);
  };

  const fetchData = async () => {
    try {
      const response = await fetch(
        "http://localhost:8000/project/project_details"
      );
      const { data } = await response.json();
      setProjectDetails(data[0]);
      console.log(data);
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <>
      {changesMade && (
        <div className="save-button-container">
          <button onClick={handleSubmit} className="save-button">
            save
          </button>
        </div>
      )}
      <div className="scope-and-stack-wrapper">
        <div className="stack-wrapper">
          <label>Select Project's Technology</label>
          <Dropdown
            searchable={false}
            className="dropdown"
            value={{
              label: projectDetails?.stack?.label,
              value: projectDetails?.stack?.value,
            }}
            onChange={(item) => handleInputChange(item, "stack")}
            options={[
              { label: "Backend", value: "backend" },
              { label: "Frontend", value: "frontend" },
              { label: "Database", value: "database" },
              { label: "Mobile-App", value: "mobile_app" },
              {
                label: "Infrasrtucture and Services",
                value: "infrastructure_and_services",
              },
            ]}
          />
        </div>
        <div className="scope-wrapper">
          <label>Scope</label>
          <textarea
            value={projectDetails?.scope}
            onChange={(e) => handleInputChange(e, "scope")}
          ></textarea>
        </div>
      </div>
    </>
  );
};

export default ScopeAndStack;
