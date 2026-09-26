/* =====================================================================
   InsureFlow - Visual DSA Playground & Evaluation Component
   Renders live DSA benchmarking, traversal outputs, and complexity reports.
   ===================================================================== */

const DSAVisualizer = {
    async runHashMapTest() {
        const target = document.getElementById('dsa-hash-policy-input')?.value || 'POL-10001';
        const res = await API.runDsaTest('hashmap', { policyNumber: target });
        
        const output = `[DSA CONCEPT]: ${res.dsaConcept}\n` +
                       `[TIME COMPLEXITY]: ${res.timeComplexity}\n` +
                       `[TOTAL INDEXED POLICIES]: ${res.totalIndexedPolicies}\n` +
                       `[EXECUTION TIME]: ${res.executionTimeNanos} Nanoseconds\n` +
                       `[KEY LOOKUP]: '${target}' -> Found: ${res.found}\n` +
                       `[FOUND POLICY DATA]: ${JSON.stringify(res.result, null, 2)}`;
        
        document.getElementById('dsa-output-box').innerText = output;
    },

    async runSearchComparison() {
        const target = document.getElementById('dsa-search-policy-input')?.value || 'POL-10001';
        const res = await API.runDsaTest('search', { target: target });

        const output = `[DSA CONCEPT]: ${res.dsaConcept}\n` +
                       `[TOTAL ITEMS IN SORTED ARRAY]: ${res.totalItems}\n` +
                       `[TARGET KEY]: '${res.target}'\n\n` +
                       `⚡ Linear Search (O(N)): Found at Index ${res.linearIndex} in ${res.linearSearchTimeNanos} ns\n` +
                       `🚀 Binary Search (O(log N)): Found at Index ${res.binaryIndex} in ${res.binarySearchTimeNanos} ns\n\n` +
                       `CONCLUSION: Binary Search is ~${(res.linearSearchTimeNanos / (res.binarySearchTimeNanos || 1)).toFixed(1)}x faster on sorted data!`;

        document.getElementById('dsa-output-box').innerText = output;
    },

    async runQuickSortTest() {
        const sortBy = document.getElementById('dsa-sort-by-select')?.value || 'premium';
        const res = await API.runDsaTest('quicksort', { sortBy: sortBy });

        const output = `[DSA CONCEPT]: ${res.dsaConcept} (${res.timeComplexity})\n` +
                       `[SORT CRITERIA]: ${res.sortedBy.toUpperCase()}\n` +
                       `[SORT EXECUTION TIME]: ${res.executionTimeNanos} Nanoseconds\n` +
                       `[TOTAL ELEMENTS SORTED]: ${res.totalSorted}\n\n` +
                       `[SORTED RESULT LIST]:\n` +
                       res.sortedList.map((p, i) => `${i + 1}. ${p.policyNumber} - ${p.customer ? p.customer.firstName + ' ' + p.customer.lastName : 'Customer'} | Premium: $${p.premiumAmount} | Coverage: $${p.coverageAmount}`).join('\n');

        document.getElementById('dsa-output-box').innerText = output;
    },

    async runPriorityQueueTest() {
        const res = await API.runDsaTest('priority-queue');

        const output = `[DSA CONCEPT]: ${res.dsaConcept} (${res.timeComplexity})\n` +
                       `[TOTAL CLAIMS IN MAX HEAP]: ${res.totalClaimsInHeap}\n\n` +
                       `[CLAIM PROCESSING PRIORITY ORDER (Urgent > High > Medium > Low)]:\n` +
                       res.prioritizedList.map((c, i) => `${i + 1}. [${c.priority}] Claim #${c.claimNumber} - Customer: ${c.customer.firstName} ${c.customer.lastName} | Claim Amount: $${c.claimAmount} | Status: ${c.status}`).join('\n');

        document.getElementById('dsa-output-box').innerText = output;
    },

    async runBSTTest() {
        const res = await API.runDsaTest('bst');

        const output = `[DSA CONCEPT]: ${res.dsaConcept}\n` +
                       `[TOTAL NODES IN BST]: ${res.totalNodes}\n\n` +
                       `[IN-ORDER TRAVERSAL RESULT (Sorted Policy Numbers)]:\n` +
                       res.inOrderTraversal.map((p, i) => `${i + 1}. Policy #${p.policyNumber} (Type: ${p.policyType.name})`).join('\n');

        document.getElementById('dsa-output-box').innerText = output;
    },

    async runGraphTest(algo = 'bfs') {
        const res = await API.runDsaTest('graph', { algorithm: algo });

        const output = `[DSA CONCEPT]: ${res.dsaConcept}\n` +
                       `[ALGORITHM EXECUTED]: ${res.algorithmUsed}\n` +
                       `[STARTING ROOT NODE]: ${res.startNodeId}\n` +
                       `[TOTAL GRAPH VERTICES]: ${res.totalNodesInGraph}\n\n` +
                       `[GRAPH TRAVERSAL ORDER (Customer -> Policy -> Claim Chains)]:\n` +
                       res.traversalResult.map((node, i) => `${i + 1}. [${node.type}] Node ID: ${node.id} | Label: ${node.label}`).join('\n');

        document.getElementById('dsa-output-box').innerText = output;
    }
};
