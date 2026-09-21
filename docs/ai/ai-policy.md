# AI Use Policy

**Owner:** Nova (AI/Governance Lead) | **Backup:** Hamza

## Purpose

This policy defines how the team will use AI-assisted tools while preserving human engineering responsibility, reviewability, traceability, and verification.

## Core Principle

The team remains responsible for all project work regardless of whether AI assisted in producing it. AI-generated or AI-assisted output is not considered correct merely because a tool produced it. Team members are responsible for understanding, reviewing, verifying, and being able to defend work they contribute to the repository. Accountability remains strictly with the human engineers.

## Permitted Uses

The team permits AI-assisted tools (such as ChatGPT, GitHub Copilot, and Gemini) to support activities such as:

- brainstorming and exploring alternatives;
- explaining technical concepts;
- reviewing requirements and engineering decisions;
- generating or reviewing draft code;
- generating or reviewing draft tests;
- debugging and defect investigation;
- refactoring suggestions;
- drafting technical documentation;
- identifying risks, edge cases, and verification concerns.

Permitted use does not remove the verification expectations defined in this policy.

## Prohibited or Unacceptable Uses

The team will not:

- submit AI-generated work that no team member understands;
- treat AI output as authoritative without human review;
- represent unverified AI-generated claims as established engineering facts;
- use AI output to fabricate requirements, test results, evidence, citations, repository history, stakeholder input, or phase-gate evidence;
- claim that testing or verification occurred when it did not;
- use AI to bypass required human review or team participation;
- provide secrets, credentials, private keys, access tokens, or other protected information to an AI system.

## Human Review Requirements

Before AI-assisted work is accepted into the project's authoritative evidence, the responsible team member must:

1. understand the relevant output;
2. review it for correctness and relevance;
3. identify assumptions or unsupported claims;
4. verify important behavior using appropriate evidence;
5. revise or reject incorrect or unsuitable output; and
6. ensure the resulting artifact is consistent with related requirements, decisions, implementation, tests, and documentation.

Significant AI-assisted implementation should receive the same engineering review expected of comparable human-written implementation.

## Verification Expectations

AI-assisted work must be verified based on its engineering significance and risk. Higher-risk AI-assisted work requires stronger independent verification. Verification may include automated tests, manual testing, code review, independent analysis, or comparison with authoritative documentation.

## AI Use Logging

Significant AI-assisted engineering activity will be recorded in:

`/docs/ai/ai-use-log.md`

## AI Verification Evidence

When AI materially contributes to engineering work that warrants explicit verification evidence, the team will document the verification in:

`/docs/ai/ai-verification-notes.md`

The AI Use Log should reference verification evidence when appropriate.

## Sensitive and Protected Information

Team members will not intentionally provide AI systems with passwords, authentication tokens, private keys, secrets, protected credentials, private personal information, or confidential information the team is not authorized to disclose.

## Engineering Decisions

AI may recommend an engineering decision, but the decision remains a team decision. Significant decisions should be recorded in the appropriate decision artifact.

## Handling Incorrect AI Output

If AI-generated or AI-assisted output is found to be incorrect, incomplete, unsafe, misleading, or unsupported, the team will:

1. reject or correct the output;
2. determine whether related work was affected;
3. update affected implementation or engineering evidence;
4. rerun appropriate verification; and
5. document the issue when it is significant to project history or evidence.

## Team Expectations

- Humans remain responsible for submitted engineering work.
- Team members must understand work they accept.
- AI output must be reviewed rather than trusted automatically.
- Verification should be proportional to engineering risk.
- Significant AI use must be traceable.
- AI must not fabricate engineering evidence.
- AI assistance does not replace peer review, testing, or engineering judgment.
- Sensitive credentials and protected information must not be disclosed.
- Uncertainty should be stated explicitly rather than hidden behind AI-generated certainty.