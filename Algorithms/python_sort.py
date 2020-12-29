def sort(lst):
    if len(lst) <= 1:
        return lst
    else:
        pivot = lst.pop(0)
        before = [i for i in lst if i < pivot]
        after = [i for i in lst if i >= pivot]
        print("before", before)
        print("after", after)
        print("Pivot", pivot)
        
    return sort(before) + [pivot] + sort(after)



a= [10,7,9,1,6,2,5,3]
print(sort(a))