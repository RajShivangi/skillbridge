import { useState } from "react";

function Jobs() {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [minimumExperience, setMinimumExperience] = useState("");
  const [skillGroups, setSkillGroups] = useState([{ name: "", skills: "" },]);

  const addSkillGroup = () => {
  setSkillGroups([
    ...skillGroups,
    { name: "", skills: "" },
  ]);
};

const updateSkillGroup = (index, field, value) => {
  const updatedGroups = [...skillGroups];
  updatedGroups[index][field] = value;
  setSkillGroups(updatedGroups);
};

const removeSkillGroup = (index) => {
  setSkillGroups(
    skillGroups.filter((_, i) => i !== index)
  );
};



  const handleSubmit = async (e) => {
    e.preventDefault();

    const job = {
      title,
      description,
      minimumExperience: Number(minimumExperience),
      skillRequirementGroups: skillGroups.map((group) => ({
        name: group.name,
        skills: group.skills
        .split(",")
        .map((skill) => skill.trim())
        .filter(Boolean),
    })),
    };

    try {
      const response = await fetch("http://localhost:8080/api/jobs", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(job),
      });

      if (!response.ok) {
        throw new Error("Failed to create job");
      }

      const savedJob = await response.json();

      console.log("Job created:", savedJob);

      setTitle("");
      setDescription("");
      setMinimumExperience("");

      alert("Job created successfully!");
    } catch (error) {
      console.error("Error creating job:", error);
    }


    const addSkillGroup = () => {
  setSkillGroups([
    ...skillGroups,
    { name: "", skills: "" },
  ]);
};

const updateSkillGroup = (index, field, value) => {
  const updatedGroups = [...skillGroups];

  updatedGroups[index][field] = value;

  setSkillGroups(updatedGroups);
};

const removeSkillGroup = (index) => {
  setSkillGroups(
    skillGroups.filter((_, i) => i !== index)
  );
};

  };

  return (
    <div>
      <h2>Create Job</h2>

      <p className="page-description">
        Enter the job details to find matching candidates.
      </p>

      <form className="job-form" onSubmit={handleSubmit}>
        <label>Job Title</label>

        <input
          type="text"
          placeholder="e.g. Software Engineer"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          required
        />

        <label>Job Description</label>

        <textarea
          placeholder="Paste the job description here..."
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          rows="8"
          required
        />

        <label>Minimum Experience</label>

        <input
          type="number"
          placeholder="e.g. 2"
          value={minimumExperience}
          onChange={(e) => setMinimumExperience(e.target.value)}
          min="0"
          required
        />


<div className="requirements-section">
  <div className="requirements-header">
    <h3>Skill Requirements</h3>

    <button
      type="button"
      className="add-requirement-btn"
      onClick={addSkillGroup}
    >
      + Add Requirement
    </button>
  </div>

    {skillGroups.map((group, index) => (
        <div className="requirement-row" key={index}>
        <input
            type="text"
            placeholder="Category e.g. Cloud Platform"
            value={group.name}
            onChange={(e) =>
            updateSkillGroup(index, "name", e.target.value)
            }
            required
        />

        <input
            type="text"
            placeholder="Skills e.g. AWS, Azure, GCP"
            value={group.skills}
            onChange={(e) =>
            updateSkillGroup(index, "skills", e.target.value)
            }
            required
        />

        {skillGroups.length > 1 && (
            <button
            type="button"
            className="remove-requirement-btn"
            onClick={() => removeSkillGroup(index)}
            >
            Remove
            </button>
        )}
        </div>
    ))}
    </div>



        <button type="submit">Create Job</button>
      </form>
    </div>
  );
}

export default Jobs;