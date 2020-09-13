def solution(n):
    primes = set([num for num in range(3, n + 1, 2)] + [2])
    
    for i in range(3, int(n ** 0.5) + 1, 2):
        primes -= set(range(i * 2, n + 1, i))

    answer = len(primes)
    
    return answer