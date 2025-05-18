# pages flow

```mermaid
    flowchart
        
        L[login/registration page]
        M[main menu]
        S[game settings]
        A[account]
        R[game rules]
        G[in game board]
        
        L --> M
        
        M --> S
        M --> A
        M --> R
        
        S --> G
        S --> R
        
        G --> R

        R --> M
        R --> S
        R --> G
        

```