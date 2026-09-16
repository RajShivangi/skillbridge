import { useEffect, useState } from "react";

function Candidates() {
  const [candidates, setCandidates] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/api/candidates")
      .then((response) => response.json())
      .then((data) => setCandidates(data))
      .catch((error) =>
        console.error("Error fetching candidates:", error)
      );
  }, []);

  return (
    <div>
      <h2>Candidates</h2>

      {candidates.length === 0 ? (
        <p>No candidates found.</p>
      ) : (
        <table className="candidate-table">
          <thead>
            <tr>
              <th>Name</th>
              <th>Email</th>
              <th>Current Title</th>
              <th>Experience</th>
              <th>Skills</th>
            </tr>
          </thead>

          <tbody>
            {candidates.map((candidate) => (
              <tr key={candidate.id}>
                <td>{candidate.name}</td>
                <td>{candidate.email}</td>
                <td>{candidate.currentTitle}</td>
                <td>{candidate.yearsExperience} years</td>
                <td>{candidate.skills?.join(", ")}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default Candidates;