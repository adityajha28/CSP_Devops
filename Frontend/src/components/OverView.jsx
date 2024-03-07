import React, { useEffect, useState } from "react";
import { Dropdown } from "monday-ui-react-core";
import "monday-ui-react-core/tokens";
import "../styling/overview.css";
import axios from "axios";


const Overview = () => {
  const [changesMade, setChangesMade] = useState(false);
  const [budgetMode, setBudgetMode] = useState({});
  const [projectDetails, setProjectDetails] = useState({
    overview: "",
    budget: {},
    timeline: "",
  });

  const handleSubmit = async () => {
    try {
      console.log("submit clicked");
      const { data } = await axios.post(
        "http://localhost:8000/project/project_details",
        {
          projectDetails,
        }
      );
      setChangesMade(false);
      console.log(data);
    } catch (error) {}
  };

  const handleInputChange = (e, field) => {
    // console.log(e);
    const newProjectDetails = { ...projectDetails };
    if (field === "Fixed" || field === "Monthly") {
      newProjectDetails["budget"] = { type: field, type_value: e.target.value };
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
      console.log("overview", data);
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
      <div className="overview-section-wrapper">
        <div className="overview-div">
          <label>Project Overview</label>
          <textarea
            value={projectDetails?.overview}
            onChange={(e) => handleInputChange(e, "overview")}
          ></textarea>
        </div>
        <div className="budget-div">
          <div className="dropdown-div">
            <label> Project Budget</label>
            <Dropdown
              className="dropdown"
              value={{
                label: projectDetails.budget.type,
                value: projectDetails.budget.type,
              }}
              searchable={false}
              onChange={(budgetMode) => {
                console.log(budgetMode);
                const budget = {
                  type: budgetMode.label,
                  type_value: "",
                };
                setProjectDetails({ ...projectDetails, budget: budget });
              }}
              options={[
                { label: "Fixed", value: "Fixed" },
                { label: "Monthly", value: "Monthly" },
              ]}
            />
          </div>
          <div className="dropdown-input-div">
            {projectDetails?.budget.type &&
              (projectDetails.budget.type === "Fixed" ? (
                <>
                  <label> Duration (in Months)</label>
                  <input
                    type="text"
                    onChange={(e) => handleInputChange(e, "duration")}
                    value={
                      projectDetails.budget?.type == "Fixed"
                        ? projectDetails.budget?.type_value
                        : ""
                    }
                  />
                </>
              ) : (
                <>
                  <label> Budgeted Hours</label>
                  <input
                    type="text"
                    onChange={(e) => handleInputChange(e, "budgeted-hours")}
                    value={
                      projectDetails.budget?.type === "Monthly"
                        ? projectDetails.budget?.type_value
                        : ""
                    }
                  />
                </>
              ))}
          </div>
        </div>
        <div className="timeline-div">
          <label> Timeline </label>
          <input
            value={projectDetails.timeline}
            type="text"
            onChange={(e) => handleInputChange(e, "timeline")}
          />
        </div>
      </div>
    </>
  );
};





export default Overview;
